package com.awrank.web.model.utils.json;

import org.codehaus.jackson.node.ObjectNode;

import java.io.Serializable;

/**
 * User: a_polyakov
 */
public interface IJsonObject extends Serializable {
    public ObjectNode toJsonObject();
}