# HoloDatePickerDialog
Android backport library for Datepicker

## Screenshots
<div align="center">
    <img src="device-2015-01-27-121101.png" alt="Example App" width="45%" />
    <img height="0" width="8px">
    <img src="device-2015-01-27-121130.png" alt="Example App" width="45%" />
</div>

## Installation

```
repositories {
    maven { url "https://raw.github.com/dgrigutsch/HoloDatePickerDialog/master/" }
}
```

```gradle
 compile 'bitninja.de:holo-datepicker:0.1.2'
 ```
 
## Usage

```
SpinnerDatePickerFragment datePickerFragment = SpinnerDatePickerFragment.newInstance(
                new CalendarDatePickerDialog.OnDateSetListener() {
                                 @Override
                                 public void onDateSet(DialogFragment dialogFragment, int i, int i2, int i3) {
                                     try {
                                         Calendar c = Calendar.getInstance();
                                         c.set(year, monthOfYear, dayOfMonth);
                                         SimpleDateFormat sdf_a = new SimpleDateFormat("yyyy-MM-dd");
                                         DateFormat sdf_b = SimpleDateFormat.getDateInstance();
                                         String mDateString = sdf_b.format(c.getTime());
                                         String mDateBackend = sdf_a.format(c.getTime());

                                         mDateEditText.setText(mDateString);
                                         data.onValueChanged(mDateBackend, data.position);
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                     }
                                 }
                             }
                );
```

This library is based on Holoeverywhere.
https://github.com/Prototik/HoloEverywhere

Copyright (c) 2015 David Grigutsch

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

