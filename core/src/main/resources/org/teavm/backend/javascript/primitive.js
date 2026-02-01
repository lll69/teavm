/*
 *  Copyright 2023 Alexey Andreev.
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
"use strict";

let $rt_createPrimitiveCls = (name, binaryName, kind) => {
    let cls = () => {};
    cls[$rt_meta] = $rt_newClassMetadata({
        name: name,
        binaryName: binaryName,
        modifiers: 1 | (1 << 4),
        primitiveKind: kind
    });
    return cls;
}
let $rt_booleancls = $rt_createPrimitiveCls("boolean", "Z", 1);
let $rt_bytecls = $rt_createPrimitiveCls("byte", "B", 2);
let $rt_shortcls = $rt_createPrimitiveCls("short", "S", 3);
let $rt_charcls = $rt_createPrimitiveCls("char", "C", 4);
let $rt_intcls = $rt_createPrimitiveCls("int", "I", 5);
let $rt_longcls = $rt_createPrimitiveCls("long", "J", 6);
let $rt_floatcls = $rt_createPrimitiveCls("float", "F", 7);
let $rt_doublecls = $rt_createPrimitiveCls("double", "D", 8);
let $rt_voidcls = $rt_createPrimitiveCls("void", "V", 9);
