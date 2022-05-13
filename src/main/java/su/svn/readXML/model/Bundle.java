/*
 * This file was last modified at 2022.05.01 22:44 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Bundle.java
 * $Id$
 */

package su.svn.readXML.model;

import lombok.Builder;

import java.util.List;

@Builder(builderClassName = "Builder")
public record Bundle(String name, String value, List<Bundle> values) {
}