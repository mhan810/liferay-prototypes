package com.liferay.support.bi.client.internal.gogo;

import com.liferay.support.bi.data.binding.JSONObjectMapper;
import com.liferay.support.bi.storage.elasticsearch.MessageStorage;

import java.io.InputStream;

import java.net.URI;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {"osgi.command.function=load", "osgi.command.scope=supportBI"},
	service = SupportBIDataLoaderGogoClient.class
)
public class SupportBIDataLoaderGogoClient {

	public void load(String fileURI) {
		URI uri = URI.create(fileURI);

		try (InputStream in = uri.toURL().openStream()) {
			Collection<String> jsonObjects = jsonObjectMapper.convert(in);

			messageStorage.store(jsonObjects);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Reference(
		target = "(model=com.liferay.support.bi.data.binding.stubs.SupportTicket)"
	)
	protected JSONObjectMapper<?> jsonObjectMapper;

	@Reference
	protected MessageStorage messageStorage;

}