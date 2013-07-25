/*
 * Kurento Commons MSControl: Simplified Media Control API for the Java Platform based on jsr309
 * Copyright (C) 2012  Tikal Technologies
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kurento.kmf.media;

import java.io.IOException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

import com.kurento.kmf.media.internal.MediaServerServiceManager;
import com.kurento.kms.api.MediaObjectNotFoundException;
import com.kurento.kms.api.MediaServerException;
import com.kurento.kms.api.MediaServerService;
import com.kurento.kms.api.MediaServerService.AsyncClient.getMediaType_call;
import com.kurento.kms.api.MediaType;

/**
 * A MediaSink receives media from a connected MediaSrc (if any)
 * 
 */
public abstract class MediaPad extends MediaObject {

	private static final long serialVersionUID = 1L;

	MediaPad(com.kurento.kms.api.MediaObject mediaPad) {
		super(mediaPad);
	}

	/* SYNC */

	/**
	 * Returns the mediaElement this MediaPad belongs to
	 * 
	 * @return The parent MediaElement
	 */
	public MediaElement getMediaElement() {
		// TODO: Implement this method using getParent method
		throw new NotImplementedException();
	}

	public MediaType getMediaType() throws IOException {
		try {
			MediaServerServiceManager manager = MediaServerServiceManager
					.getInstance();
			MediaServerService.Client service = manager.getMediaServerService();
			MediaType mediaType = service.getMediaType(mediaObject);
			manager.releaseMediaServerService(service);
			return mediaType;
		} catch (MediaObjectNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (MediaServerException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TException e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/* ASYNC */

	/**
	 * Returns the mediaElement this MediaPad belongs to
	 * 
	 * @param cont
	 *            The continuation to receive the result
	 * @return The parent MediaSrc
	 */
	public void getMediaElement(Continuation<MediaElement> cont) {
		// TODO: Implement this method using getParent method
		throw new NotImplementedException();
	}

	public void getMediaType(final Continuation<MediaType> cont)
			throws IOException {
		try {
			MediaServerServiceManager manager = MediaServerServiceManager
					.getInstance();
			MediaServerService.AsyncClient service = manager
					.getMediaServerServiceAsync();
			service.getMediaType(
					mediaObject,
					new AsyncMethodCallback<MediaServerService.AsyncClient.getMediaType_call>() {
						@Override
						public void onComplete(getMediaType_call response) {
							try {
								MediaType mediaType = response.getResult();
								cont.onSuccess(mediaType);
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
			manager.releaseMediaServerServiceAsync(service);
		} catch (TException e) {
			throw new IOException(e.getMessage(), e);
		}
	}
}