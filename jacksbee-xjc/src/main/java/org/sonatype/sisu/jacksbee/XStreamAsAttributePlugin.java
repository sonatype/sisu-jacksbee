/*
 * Copyright (c) 2007-2012 Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.sonatype.sisu.jacksbee;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JFieldVar;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.xml.bind.v2.model.core.PropertyKind;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

/**
 * Adds {@link XStreamAsAttribute} to XSD attribute fields.
 *
 * @since 1.0
 */
public class XStreamAsAttributePlugin
    extends AbstractParameterizablePlugin
{
    @Override
    public String getOptionName() {
        return "XxstreamAsAttribute";
    }

    @Override
    public String getUsage() {
        return "Adds @XStreamAsAttribute to XSD attribute fields.";
    }

    @Override
    protected boolean run(final Outline outline, final Options options) throws Exception {
        assert outline != null;
        assert options != null;

        for (ClassOutline type : outline.getClasses()) {
            processClassOutline(type);
        }

        return true;
    }

    private void processClassOutline(final ClassOutline outline) {
        assert outline != null;

        for (FieldOutline field : outline.getDeclaredFields()) {
            processFieldOutline(field);
        }
    }

    private void processFieldOutline(final FieldOutline outline) {
        assert outline != null;

        PropertyKind kind = outline.getPropertyInfo().kind();
        if (kind == PropertyKind.ATTRIBUTE) {
            JFieldVar field = FieldAccessorUtils.field(outline);
            JAnnotationUse anno = field.annotate(XStreamAsAttribute.class);
        }
    }
}