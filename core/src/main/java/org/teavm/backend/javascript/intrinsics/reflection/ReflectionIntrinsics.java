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

import java.util.Map;
import org.teavm.backend.javascript.spi.Generator;
import org.teavm.backend.javascript.spi.Injector;
import org.teavm.model.MethodReference;
import org.teavm.runtime.StringInfo;
import org.teavm.runtime.reflect.AnnotationInfo;
import org.teavm.runtime.reflect.ClassInfo;
import org.teavm.runtime.reflect.ClassReflectionInfo;
import org.teavm.runtime.reflect.FieldInfo;
import org.teavm.runtime.reflect.MethodInfo;
import org.teavm.runtime.reflect.TypeVariableInfo;

public class ReflectionIntrinsics {
    private Map<MethodReference, Injector> injectors;
    private Map<MethodReference, Generator> generators;

    public ReflectionIntrinsics(Map<MethodReference, Injector> injectors, Map<MethodReference, Generator> generators) {
        this.injectors = injectors;
        this.generators = generators;
    }

    public void apply() {
        var classGen = new ClassInfoGenerator();
        injectors.put(new MethodReference(ClassInfo.class, "classObject", Class.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "primitiveKind", int.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "modifiers", int.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "name", StringInfo.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "itemType", ClassInfo.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "parent", ClassInfo.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "simpleName", StringInfo.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "declaringClass", ClassInfo.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "enclosingClass", ClassInfo.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "superinterfaceCount", int.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "superinterface", int.class, ClassInfo.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "isSuperTypeOf", ClassInfo.class, boolean.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "initialize", void.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "newInstance", Object.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "initializeNewInstance", Object.class, void.class),
                classGen);
        injectors.put(new MethodReference(ClassInfo.class, "arrayType", ClassInfo.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "enumConstantCount", int.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "enumConstant", int.class, Object.class), classGen);
        generators.put(new MethodReference(ClassInfo.class, "newArrayInstance", int.class, Object.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "arrayLength", Object.class, int.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "getItem", Object.class, int.class, Object.class),
                classGen);
        injectors.put(new MethodReference(ClassInfo.class, "putItem", Object.class, int.class, Object.class,
                void.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "reflection", ClassReflectionInfo.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "rewind", void.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "hasNext", boolean.class), classGen);
        injectors.put(new MethodReference(ClassInfo.class, "next", ClassInfo.class), classGen);

        var reflectionGen = new ClassReflectionInfoGenerator();
        injectors.put(new MethodReference(ClassReflectionInfo.class, "annotationCount", int.class), reflectionGen);
        injectors.put(new MethodReference(ClassReflectionInfo.class, "annotation", int.class, AnnotationInfo.class),
                reflectionGen);
        injectors.put(new MethodReference(ClassReflectionInfo.class, "fieldCount", int.class), reflectionGen);
        injectors.put(new MethodReference(ClassReflectionInfo.class, "field", int.class, FieldInfo.class),
                reflectionGen);
        injectors.put(new MethodReference(ClassReflectionInfo.class, "methodCount", int.class), reflectionGen);
        injectors.put(new MethodReference(ClassReflectionInfo.class, "method", int.class, MethodInfo.class),
                reflectionGen);
        injectors.put(new MethodReference(ClassReflectionInfo.class, "typeParameterCount", int.class), reflectionGen);
        injectors.put(new MethodReference(ClassReflectionInfo.class, "typeParameterCount", int.class,
                TypeVariableInfo.class), reflectionGen);

        injectors.put(new MethodReference(StringInfo.class, "getStringObject", String.class),
                new StringInfoGenerator());

        generators.put(new MethodReference(Object.class, "getClassInfo", ClassInfo.class), new ObjectGenerator());
    }
}
