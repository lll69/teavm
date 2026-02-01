/*
 *  Copyright 2026 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.backend.javascript.intrinsics.reflection;

import org.teavm.backend.javascript.spi.Injector;
import org.teavm.backend.javascript.spi.InjectorContext;
import org.teavm.model.MethodReference;

public class ClassReflectionInfoGenerator implements Injector {
    @Override
    public void generate(InjectorContext context, MethodReference methodRef) {
        switch (methodRef.getDescriptor().getName()) {
            case "annotationCount":
                writeArrayLength(context, "annotations");
                break;
            case "annotation":
                writeArrayGet(context, "annotations");
                break;
            case "fieldCount":
                writeArrayLength(context, "fields");
                break;
            case "field":
                writeArrayGet(context, "fields");
                break;
            case "methodCount":
                writeArrayLength(context, "methods");
                break;
            case "method":
                writeArrayGet(context, "methods");
                break;
            case "typeParameterCount":
                writeArrayLength(context, "typeParameters");
                break;
            case "typeParameter":
                writeArrayGet(context, "typeParameters");
                break;
        }
    }

    private void writeArrayLength(InjectorContext context, String name) {
        context.writeExpr(context.getArgument(0));
        context.getWriter().append("." + name + ".length");
    }

    private void writeArrayGet(InjectorContext context, String name) {
        context.writeExpr(context.getArgument(0));
        context.getWriter().append("." + name + "[");
        context.writeExpr(context.getArgument(1));
        context.getWriter().append("]");
    }
}
