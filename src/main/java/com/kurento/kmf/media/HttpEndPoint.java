package com.kurento.kmf.media;

import java.io.IOException;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

import com.kurento.kms.api.MediaObjectId;
import com.kurento.kms.api.MediaObjectNotFoundException;
import com.kurento.kms.api.MediaServerException;
import com.kurento.kms.api.MediaServerService;
import com.kurento.kms.api.MediaServerService.AsyncClient.getUrl_call;

/**
 * Represents a http address where a single get or post can be done
 * 
 * @author jcaden
 * 
 */
public class HttpEndPoint extends EndPoint {

	private static final long serialVersionUID = 1L;

	HttpEndPoint(MediaObjectId httpEndPointId) {
		super(httpEndPointId);
	}

	/* SYNC */

	public String getUrl() throws IOException {
		MediaServerService.Client service = mssm.getMediaServerService();

		try {
			return service.getUrl(mediaObjectId);
		} catch (MediaObjectNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (MediaServerException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TException e) {
			throw new IOException(e.getMessage(), e);
		} finally {
			mssm.releaseMediaServerService(service);
		}
	}

	/* ASYNC */

	public void getUrl(final Continuation<String> cont) throws IOException {
		MediaServerService.AsyncClient service = mssm
				.getMediaServerServiceAsync();

		try {
			service.getUrl(
					mediaObjectId,
					new AsyncMethodCallback<MediaServerService.AsyncClient.getUrl_call>() {
						@Override
						public void onComplete(getUrl_call response) {
							try {
								cont.onSuccess(response.getResult());
							} catch (MediaObjectNotFoundException e) {
								cont.onError(new RuntimeException(e
										.getMessage(), e));
							} catch (MediaServerException e) {
								cont.onError(new RuntimeException(e
										.getMessage(), e));
							} catch (TException e) {
								cont.onError(new IOException(e.getMessage(), e));
							}
						}

						@Override
						public void onError(Exception exception) {
							cont.onError(exception);
						}
					});
		} catch (TException e) {
			throw new IOException(e.getMessage(), e);
		} finally {
			mssm.releaseMediaServerServiceAsync(service);
		}
	}

}