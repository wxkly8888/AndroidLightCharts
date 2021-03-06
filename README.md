# AndroidLightCharts
A light-weight android charts library include Line Chart, Bar Chart and Pie Chart. BaseOn:

[AndroidCharts]: https://github.com/HackPlan/AndroidCharts	"AndroidCharts"

Screenshots:

![lineview](https://github.com/wxkly8888/AndroidLightCharts/blob/main/screenshots/Screenshot_lineview.jpg)

![barview](https://github.com/wxkly8888/AndroidLightCharts/blob/main/screenshots/Screenshot_barview.jpg)

![pieview](https://github.com/wxkly8888/AndroidLightCharts/blob/main/screenshots/Screenshot_pieview.jpg)

properties that are Configurable

```xml


<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="LineView">
        <!--the color of  labels  on X-axis-->
        <attr name="x_text_color" format="color" />
        <!--the space between labels  on  X-axis-->
        <attr name="x_text_space" format="integer" />
        <!--the text size of labels  on  X-axis -->
        <attr name="x_text_size" format="integer" />
        <!--the color of labels  on Y-axis -->
        <attr name="y_text_color" format="color" />
        <!--the text size of labels  on Y-axis -->
        <attr name="y_text_size" format="integer" />
        <!--the type of popup text on the top of line -->
        <attr name="show_popup_type" format="integer" />
        <!--the color of background lines-->
        <attr name="background_line_color" format="color" />
    </declare-styleable>
      <declare-styleable name="barView">
        <!--the color of  labels  on X-axis-->
        <attr name="bar_x_text_color" format="color" />
        <!--the space between labels  on  X-axis-->
        <attr name="bar_x_text_space" format="integer" />
        <!--the text size of labels  on  X-axis -->
        <attr name="bar_x_text_size" format="integer" />
        <!--the color of labels  on Y-axis -->
        <attr name="bar_y_text_color" format="color" />
        <!--the text size of labels  on Y-axis -->
        <attr name="bar_y_text_size" format="integer" />
        <!--the color of background lines-->
        <attr name="bar_background_line_color" format="color" />
        <!--the color of the bar-->
        <attr name="bar_color" format="color" />
        <!--the width of the bar-->
        <attr name="bar_width" format="integer" />
        <!--the text color of the popup text-->
        <attr name="bar_pop_textcolor" format="color" />
        <!--whether to show the popup text-->
        <attr name="bar_show_pop_text" format="boolean" />
    </declare-styleable>

    <declare-styleable name="pieView">
        <!--whether to show the boarder line -->
        <attr name="show_boarder_line" format="boolean" />
        <!--whether to show the percent text -->
        <attr name="show_percent_text" format="boolean" />
    </declare-styleable>
</resources>
    
```

