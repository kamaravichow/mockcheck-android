# MockCheck

A lightweight library to check if user is faking their location or using a mocklocation to fool your apps.

## Dependency

```groovy
implementation 'io.github.kamaravichow:mockcheck:0.1.0
```

Latest version : [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.kamaravichow/mockcheck/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.kamaravichow/mockcheck)


## Usage

You can just call isMockedLocation method to get a boolean response of mock status
```kotlin
if (MockCheck(context).isMockedLocation){
       // user is faking their location
}
```

[**Advanced Usage Documentation**](https://docs.aravi.me/android/mockcheck/usage)

## FAQ

**What is MockLocation ?**

Both iOS and Android created MockedLocation as a developer feature years ago. This allowed developers to test location-aware features on their apps and was only enabled using explicit settings on developer-mode or the simulator. But a few users might use this feature for their advantage, maybe for unlocking location based stuff. **This is where this library helps you**


## License
Copyright 2022 Aravind Chowdary (@kamaravichow)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
