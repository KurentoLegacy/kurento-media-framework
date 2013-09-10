package com.kurento.kmf.media;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kurento.kms.api.FilterType;
import com.kurento.kms.api.MediaObjectId;

public class JackVaderFilter extends Filter {
	private static final long serialVersionUID = 1L;

	static final FilterType filterType = FilterType.JACK_VADER_FILTER;

	public static final Logger log = LoggerFactory
			.getLogger(JackVaderFilter.class);

	JackVaderFilter(MediaObjectId filterId) {
		super(filterId);
	}

}