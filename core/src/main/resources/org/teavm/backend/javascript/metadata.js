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

let $rt_packageData = null;
let $rt_packages = data => {
    let i = 0;
    let packages = new teavm_globals.Array(data.length);
    for (let j = 0; j < data.length; ++j) {
        let prefixIndex = data[i++];
        let prefix = prefixIndex >= 0 ? packages[prefixIndex] : "";
        packages[j] = prefix + data[i++] + ".";
    }
    $rt_packageData = packages;
}
let $rt_allClasses = [];
let $rt_allClassesPointer = 0;
let $rt_allClassesRewind = () => $rt_allClassesPointer = 0;
let $rt_allClassesHasNext = () => $rt_allClassesPointer < $rt_allClasses.length;
let $rt_allClassesNext = () => $rt_allClasses[$rt_allClassesPointer++];
let $rt_metadata = data => {
    let packages = $rt_packageData;
    let i = 0;
    while (i < data.length) {
        let cls = data[i++];
        $rt_allClasses.push(cls);
        let m = $rt_newClassMetadata();
        cls[$rt_meta] = m;
        let className = data[i++];

        m.name = className !== 0 ? className : null;
        if (m.name !== null) {
            let packageIndex = data[i++];
            if (packageIndex >= 0) {
                m.name = packages[packageIndex] + m.name;
            }
        }

        m.binaryName = "L" + m.name + ";";
        let superclass = data[i++];
        m.parent = superclass !== 0 ? superclass : null;
        m.superinterfaces = data[i++];
        if (m.parent) {
            cls.prototype = teavm_globals.Object.create(m.parent.prototype);
        } else {
            cls.prototype = {};
        }
        cls.prototype.constructor = cls;
        m.modifiers = data[i++];
        m.primitiveKind = 0;

        let innerClassInfo = data[i++];
        if (innerClassInfo !== 0) {
            let enclosingClass = innerClassInfo[0];
            m.enclosingClass = enclosingClass !== 0 ? enclosingClass : null;
            let declaringClass = innerClassInfo[1];
            m.declaringClass = declaringClass !== 0 ? declaringClass : null;
            let simpleName = innerClassInfo[2];
            m.simpleName = simpleName !== 0 ? simpleName : null;
        }

        let clinit = data[i++];
        m.clinit = clinit !== 0
            ? () => { m.clinit = () => {}; clinit(); }
            : () => {};

        let virtualMethods = data[i++];
        if (virtualMethods !== 0) {
            for (let j = 0; j < virtualMethods.length; j += 2) {
                let name = virtualMethods[j];
                let func = virtualMethods[j + 1];
                if (typeof name === 'string') {
                    name = [name];
                }
                for (let k = 0; k < name.length; ++k) {
                    cls.prototype[name[k]] = func;
                }
            }
        }
    }
}
let $rt_enumConstantsMetadata = data => {
    let i = 0;
    while (i < data.length) {
        let cls = data[i++];
        cls[$rt_meta].enumConstants = data[i++];
    }
};