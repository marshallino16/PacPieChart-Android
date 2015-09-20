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

![preview](http://185.14.185.122/github/pacpie_preview.png)

Usage
----

define pie view in your layout.xml

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

Define your pie slices List

```java
List<PacpieSlice> slices =new ArrayList<>();

PacpieSlice slice = new PacpieSlice();
slice.count = 30;
slice.color = Color.parseColor("#EEECEC");

//add slice to the list
slices.add(item);
```

And finaly set your slice list

```java
Pacpie pacpie = (Pacpie)findViewById(R.id.pacpieChart);
pacpie.setValues(generateFakeSlices());
```

License
---

```text
The MIT License (MIT)

Copyright (c) 2014 Fernandez Anthony

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
