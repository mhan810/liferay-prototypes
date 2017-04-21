package com.liferay.support.bi.storage.elasticsearch.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.support.bi.storage.elasticsearch.MessageStorage;
import com.liferay.support.bi.storage.elasticsearch.internal.configuration.ElasticsearchMessageStorageConfiguration;

import java.net.InetAddress;

import java.util.Collection;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.support.bi.storage.elasticsearch.internal.configuration.ElasticsearchMessageStorageConfiguration",
	immediate = true, service = MessageStorage.class
)
public class ElasticsearchMessageStorageImpl implements MessageStorage {

	public void store(Collection<String> jsonObjects) throws PortalException {
		try {
			BulkRequestBuilder bulkRequestBuilder =
				transportClient.prepareBulk();

			for (String jsonObject : jsonObjects) {
				IndexRequestBuilder indexRequestBuilder =
					transportClient.prepareIndex(
						getIndexName(), _SUPPORT_TICKET_TYPE);

				indexRequestBuilder.setSource(jsonObject, XContentType.JSON);

				bulkRequestBuilder.add(indexRequestBuilder);
			}

			BulkResponse bulkResponse = bulkRequestBuilder.get();

			LogUtil.logActionResponse(_log, bulkResponse);
		}
		catch (Exception e) {
			throw new PortalException("Unable to insert data" + jsonObjects, e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) throws Exception {
		_elasticsearchMessageStorageConfiguration =
			ConfigurableUtil.createConfigurable(
				ElasticsearchMessageStorageConfiguration.class, properties);

		Settings.Builder settingsBuilder = Settings.builder();

		settingsBuilder.put(
			"client.transport.ignore_cluster_name",
			_elasticsearchMessageStorageConfiguration.
				clientTransportIgnoreClusterName());
		settingsBuilder.put(
			"client.transport.nodes_sampler_interval",
			_elasticsearchMessageStorageConfiguration.
				clientTransportNodesSamplerInterval());
		settingsBuilder.put(
			"client.transport.sniff",
			_elasticsearchMessageStorageConfiguration.clientTransportSniff());

		settingsBuilder.put("node.master", false);
		settingsBuilder.put("node.data", false);
		settingsBuilder.put("node.ingest", false);
		settingsBuilder.put("search.remote.connect", false);

		settingsBuilder.put(
			"cluster.name",
			_elasticsearchMessageStorageConfiguration.clusterName());
		settingsBuilder.put(
			"path.logs", props.get(PropsKeys.LIFERAY_HOME) + "/logs");

		transportClient = new PreBuiltTransportClient(settingsBuilder.build());

		String[] transportAddresses =
			_elasticsearchMessageStorageConfiguration.transportAddresses();

		for (String transportAddress : transportAddresses) {
			String[] transportAddressParts = StringUtil.split(
				transportAddress, StringPool.COLON);

			String host = transportAddressParts[0];

			int port = GetterUtil.getInteger(transportAddressParts[1]);

			InetAddress inetAddress = InetAddress.getByName(host);

			transportClient.addTransportAddress(
				new InetSocketTransportAddress(inetAddress, port));
		}

		checkIndices();
	}

	protected void checkIndices() throws Exception {
		IndicesAdminClient indicesAdminClient =
			transportClient.admin().indices();

		String indexName = getIndexName();

		if (hasIndex(indicesAdminClient, indexName)) {
			return;
		}

		createIndex(indexName, indicesAdminClient);
	}

	protected void createIndex(
			String indexName, IndicesAdminClient indicesAdminClient)
		throws Exception {

		CreateIndexRequestBuilder createIndexRequestBuilder =
			indicesAdminClient.prepareCreate(indexName);

		String mappings = ResourceUtil.getResourceAsString(
			getClass(), "/META-INF/mappings.json");

		createIndexRequestBuilder.addMapping(
			_SUPPORT_TICKET_TYPE, mappings, XContentType.JSON);

		Settings.Builder builder = Settings.builder();

		String settings = ResourceUtil.getResourceAsString(
			getClass(), "/META-INF/settings.json");

		builder.loadFromSource(settings, XContentType.JSON);

		createIndexRequestBuilder.setSettings(builder);

		CreateIndexResponse createIndexResponse =
			createIndexRequestBuilder.get();

		LogUtil.logActionResponse(_log, createIndexResponse);
	}

	@Deactivate
	protected void deactivate() {
		transportClient.close();
	}

	protected String getIndexName() {
		return _elasticsearchMessageStorageConfiguration.indexNamePrefix();
	}

	protected boolean hasIndex(
			IndicesAdminClient indicesAdminClient, String indexName)
		throws Exception {

		IndicesExistsRequestBuilder indicesExistsRequestBuilder =
			indicesAdminClient.prepareExists(indexName);

		IndicesExistsResponse indicesExistsResponse =
			indicesExistsRequestBuilder.get();

		return indicesExistsResponse.isExists();
	}

	@Reference
	protected Props props;

	protected TransportClient transportClient;

	private static final String _SUPPORT_TICKET_TYPE = "supportTicket";

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchMessageStorageImpl.class);

	private volatile ElasticsearchMessageStorageConfiguration
		_elasticsearchMessageStorageConfiguration;

}