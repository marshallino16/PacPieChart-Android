PacPie Chart
=========

![banner](http://185.14.185.122/github/pacpie.png)

PacPie is a small library to generate Pie Chart as an ImageView (will be different soon).

  - Easy customization
  - Use your colors for fill & stroke
  - Add percents
  - Add a background color if needed
  - Hide or show pie chart with animation (comming soon)

Version
----
1.0.1

Compatibility
----
Should be compatible with all SDK version (back to 1).

Preview
---

![preview](http://img15.hostingpics.net/thumbs/mini_229420Capturedcran20150920235603.png)

How to use
----

1. Declare gradle dependency

```
repositories {
    jcenter()
}

dependencies {
    compile 'com.github.navasmdc:MaterialDesign:1.5@aar'
}
```

2. Define pie view in your layout.xml

```java
<com.genyus.pacpiechart.library.Pacpie
        android:id="@+id/pacpieChart"
        xmlns:pacpie="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="10dp"
        pacpie:activate_rotation="true"
        pacpie:lineAntiAlias="true"
        pacpie:lineDefaultColor="@color/default_line_color"
        pacpie:lineStrokeWidth="3.0"
        pacpie:sliceAntiAlias="true"
        pacpie:sliceDefaultColor="@color/default_slice_color"
        pacpie:sliceStrokeWidth="0.0"/>
```

3. Define your pie slices List

```java
List<PacpieSlice> slices =new ArrayList<>();

PacpieSlice slice = new PacpieSlice();
slice.count = 30;
slice.color = Color.parseColor("#EEECEC");

//add slice to the list
slices.add(item);
```

4. And finaly set your slice list

```java
Pacpie pacpie = (Pacpie)findViewById(R.id.pacpieChart);
pacpie.setValues(generateFakeSlices());
```

License
---

```text
Copyright 2014 Fernandez anthony

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
