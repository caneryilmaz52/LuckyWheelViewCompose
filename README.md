<a id="readme-top"></a>

# Lucky Wheel View Compose

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

[![JitPack Version][jitpack-version-shield]][jitpack-url]
[![JitPack Download Week][jitpack-download-week-shield]][jitpack-url]
[![JitPack Download Month][jitpack-download-month-shield]][jitpack-url]

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  </br>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#xml">XML</a></li>
    <li><a href="#installation">Installation</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#examples">Examples</a></li>
    <li><a href="#customization">Customization</a></li>
    <li><a href="#support">Support</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

There are many great Lucky Wheel View available on GitHub; however, I didn't find one that really
suited my needs so I created this enhanced one. I want to create a Lucky Wheel View so amazing that
it'll be the last one you ever need -- I think this is it.

Here's why:

* Almost all views/elements can be customize
* Almost no need for work on logic, all logic is settle
* Gradient/Solid color views/elements
* Nice and smooth animations
* Almost all events can listenable
* Random or specific target can be set
* Clockwise and counterclockwise rotate direction support
* XML and Jetpack Compose support

Of course, your needs may be different. So I'll be adding more in the near future. You may also
suggest changes by forking this repo and creating a pull request or opening an issue. Thanks to all
the people have contributed to expanding this library!

Use the `Lucky Wheel View Compose` to get started.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- XML -->

## XML

`Lucky Wheel View Compose` has XML support. Check
<a href="https://github.com/caneryilmaz52/LuckyWheelView">Lucky Wheel View</a> to use.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- INSTALLATION -->

## Installation

1. Add it in your root `build.gradle` at the end of repositories:

  ```gradle
 	allprojects {
 		repositories {
 			maven { url 'https://jitpack.io' }
 		}
 	}
  ```

or

```gradle
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

2. Add the dependency

  ```gradle
 dependencies {
	        implementation 'com.github.caneryilmaz52:LuckyWheelViewCompose:LATEST_VERSION'
	}
  ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- USAGE -->

## Usage

Populate a list of `WheelData`

`text` is wheel item text

`textColor` is color of item text

- if `textColor` size = 1 then gradient text color disable and text color will be value of `textColor[0]`
- if `textColor` size > 1 then gradient text color enable
- if `textColor` is empty then wheel view is not drawn

`backgroundColor` is background color of item

- if `backgroundColor` size = 1 then gradient background color disable and background color will be  value of `backgroundColor[0]`
- if `backgroundColor` size > 1 then gradient background color enable
- if `backgroundColor` is empty then wheel view is not drawn

`textFontId` custom font id of item text

`icon` is item icon `ImageBitmap`, if not null then icon will be drawn

```kotlin
val wheelData = ArrayList<WheelData>()
val item = WheelData(
    text = itemText,
    textColor = listOf(textColor),
    backgroundColor = listOf(backgroundColor),
    textFontId = itemTextFontId,  //optional
    icon = itemImageBitmap //optional
)
wheelData.add(item)
```

Set data to `LuckyWheelView`

Set winner target (default is 0)

Set `onRotationComplete` listener to `LuckyWheelView`

Set `onRotationStatus` listener to `LuckyWheelView` if you need

Set `wheelViewState` state to `LuckyWheelView`


```kotlin
@Composable
fun LuckyWheel(wheelItems: List<WheelData>) {

  val context = LocalContext.current

  var startRotate by remember {
    mutableStateOf(false)
  }
  val wheelViewState by remember(startRotate) {
    mutableStateOf(WheelViewState(startRotate))
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(170, 170, 170)),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    LuckyWheelView(
      modifier = Modifier
        .width(350.dp)
        .height(350.dp)
        .padding(vertical = 30.dp),
      wheelItems = wheelItems,
      target = 3,
      onRotationComplete = { wheelData -> 
        // do something with winner wheel data
        Toast.makeText(context, wheelData.text, Toast.LENGTH_LONG).show()
      },
      onRotationStatus = { status ->
        when (status) {
          RotationStatus.ROTATING -> { // do something
          }

          RotationStatus.IDLE -> { // do something
          }

          RotationStatus.COMPLETED -> { // do something
          }

          RotationStatus.CANCELED -> { // do something
          }
        }
      },
      wheelViewState = wheelViewState
    )

    Button(modifier = Modifier.padding(top = 20.dp), onClick = {
      startRotate = true
    }) {
      Text("Spin!")
    }
  }
}
```

<!-- EXAMPLES -->

## Examples

<img src="https://github.com/caneryilmaz52/LuckyWheelViewCompose/blob/main/images/gif1.gif" width="250" height="250"/> <img src="https://github.com/caneryilmaz52/LuckyWheelViewCompose/blob/main/images/gif2.gif" width="250" height="250"/> <img src="https://github.com/caneryilmaz52/LuckyWheelViewCompose/blob/main/images/gif3.gif" width="250" height="250"/>

<img src="https://github.com/caneryilmaz52/LuckyWheelViewCompose/blob/main/images/gif4.gif" width="250" height="250"/> <img src="https://github.com/caneryilmaz52/LuckyWheelViewCompose/blob/main/images/gif5.gif" width="250" height="250"/> <img src="https://github.com/caneryilmaz52/LuckyWheelViewCompose/blob/main/images/gif6.gif" width="250" height="250"/>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CUSTOMIZATION -->

## Customization

Get the perfect look with customization combinations.
</br></br>

| Parameter Name         | Description                                                                                                                                                                                                                                                                                 |
|------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `modifier`             | the Modifier to be applied to this `LuckyWheelView`                                                                                                                                                                                                                                         |
| `wheelItems`           | a MutableList of `WheelData`                                                                                                                                                                                                                                                                |
| `target`               | index of the item to win <ul><li>`target` must be between 0 and wheelItems last index (exclusive)</li> <li>if target a negative number then target throw `IllegalArgumentException`</li> <li>if target bigger than `wheelItems` last index then throw `IndexOutOfBoundsException`</li></ul> |
| `randomRotateStyle`    | check info for `Random Rotate Style` section                                                                                                                                                                                                                                                |
| `iconPositionFraction` | icon vertical position fraction in wheel slice <ul><li>The smaller the value, the closer to the center</li> <li>The larger the value, the closer to the corners</li></ul>                                                                                                                   |
| `arrowStyle`           | check info for `Arrow Style` section                                                                                                                                                                                                                                                        |
| `centerPointStyle`     | check info for `Center Point Style` section                                                                                                                                                                                                                                                 |
| `cornerPointsStyle`    | check info for `Corner Points Style` section                                                                                                                                                                                                                                                |
| `centerTextStyle`      | check info for `Center Text Style` section                                                                                                                                                                                                                                                  |
| `centerImageStyle`     | check info for `Center Image Style` section                                                                                                                                                                                                                                                 |
| `itemSeparatorStyle`   | check info for `Item Separator Style` section                                                                                                                                                                                                                                               |
| `itemTextStyle`        | check info for `Item Text Style` section                                                                                                                                                                                                                                                    |
| `rotateStyle`          | check info for `Rotate Style` section                                                                                                                                                                                                                                                       |
| `rotateViaSwipeStyle`  | check info for `Rotate Via Swipe Style` section                                                                                                                                                                                                                                             |
| `strokeStyle`          | check info for `Stroke Style` section                                                                                                                                                                                                                                                       |
| `onRotationComplete`   | invoke when wheel stop. return `wheelItems[target]` value                                                                                                                                                                                                                                   |
| `onRotationStatus`     | invoke when wheel rotation status change. return current wheel `RotationStatus` value                                                                                                                                                                                                       |
| `wheelViewState`       | state of wheel rotate, if is value `WheelViewState(startRotate = true)` then `LuckyWheelView` will start rotating                                                                                                                                                                           |


```kotlin
@Composable
fun LuckyWheelView(
    modifier: Modifier,
    wheelItems: List<WheelData>,
    target: Int = 0,
    randomRotateStyle: RandomRotateStyle = RandomRotateStyle(),
    @FloatRange(from = 0.1, to = 0.9) iconPositionFraction: Float = 0.5F,
    arrowStyle: ArrowStyle = ArrowStyle(),
    centerPointStyle: CenterPointStyle = CenterPointStyle(),
    cornerPointsStyle: CornerPointsStyle = CornerPointsStyle(),
    centerTextStyle: CenterTextStyle = CenterTextStyle(text = ""),
    centerImageStyle: CenterImageStyle = CenterImageStyle(),
    itemSeparatorStyle: ItemSeparatorStyle = ItemSeparatorStyle(),
    itemTextStyle: ItemTextStyle = ItemTextStyle(),
    rotateStyle: RotateStyle = RotateStyle(),
    rotateViaSwipeStyle: RotateViaSwipeStyle = RotateViaSwipeStyle(),
    strokeStyle: StrokeStyle = StrokeStyle(),
    onRotationComplete: (WheelData) -> Unit,
    onRotationStatus: (RotationStatus) -> Unit = {},
    wheelViewState: WheelViewState
)
```

<details>
  <summary>Random Rotate Style</summary>
  </br>

```kotlin
/**
 * @param rotateToRandomTarget
 * * default is `false`
 * - if `true` and [randomTargets] is null then win index will be randomly between `0` and `wheelItems.latsIndex`
 * - if `true` and [randomTargets] is not null then win index will be randomly one of members of [randomTargets] array
 * @param randomTargets is array of win index
 */
data class RandomRotateStyle(
    val rotateToRandomTarget: Boolean = false,
    val randomTargets: IntArray? = null,
) : Serializable
```
</details>

<details>
  <summary>Arrow Style</summary>
  </br>

```kotlin
/**
 * @param arrowId is wheel arrow drawable resource id
 * @param arrowSizeDp is size of wheel arrow image
 * @param arrowColor is wheel arrow tint color
 * @param arrowPosition is wheel arrow position [ArrowPosition.TOP] or [ArrowPosition.CENTER]
 * @param arrowOffsetY
 * * is wheel arrow offset on Y axis
 * - if value is positive then arrow moving down
 * - if value is negative then arrow moving up
 * @param arrowAnimationStatus is enable or disable arrow swing animation
 * @param arrowSwingDistanceDp is arrow right and left swing distance
 * @param arrowSwingDurationMs is single arrow swing animation duration
 * @param arrowSwingSlowdownMultiplier
 * * is arrow swing animation duration slowdown speed
 * - The smaller the value, the later it slows down
 * - The larger the value, the faster it slows down
 */
data class ArrowStyle(
    @DrawableRes val arrowId: Int = R.drawable.ic_top_arrow,
    val arrowSizeDp: Dp = 48.dp,
    val arrowColor: Color = Color.Unspecified,
    val arrowPosition: ArrowPosition = ArrowPosition.TOP,
    val arrowOffsetY: Dp = 0.dp,
    val arrowAnimationStatus: Boolean = true,
    val arrowSwingDistanceDp: Dp = 10.dp,
    val arrowSwingDurationMs: Int = 50,
    val arrowSwingSlowdownMultiplier: Float = 0.1F
) : Serializable
```

</details>

<details>
  <summary>Center Point Style</summary>
  </br>

```kotlin
/**
 * @param drawCenterPoint is enable or disable center point drawing
 * @param centerPointColor is color of center point
 * @param centerPointRadiusDp is radius of center point
 */
data class CenterPointStyle(
    val drawCenterPoint: Boolean = false,
    val centerPointColor: Color = Color.White,
    val centerPointRadiusDp: Dp = 20.dp
) : Serializable
```

</details>

<details>
  <summary>Corner Points Style</summary>
  </br>

```kotlin
/**
 * @param drawCornerPoints is enable or disable corner points drawing
 * @param cornerPointsEachSlice is count of point in a slice
 * @param cornerPointsColor
 * * is colors of corner points
 * - if [cornerPointsColor] is empty and [useRandomCornerPointsColor] is `false` then corner colors will be randomly
 * - if [cornerPointsColor] is not empty and [useRandomCornerPointsColor] is `true` then corner colors will be randomly
 * @param useRandomCornerPointsColor is enable or disable random corner points colors
 * @param useCornerPointsGlowEffect is enable or disable corner points glow effect
 * @param cornerPointsColorChangeSpeedMs is corner points color change duration
 * @param cornerPointsRadiusDp is radius of corner point
 */
data class CornerPointsStyle(
    val drawCornerPoints: Boolean = false,
    val cornerPointsEachSlice: Int = 1,
    val cornerPointsColor: MutableList<Color> = mutableListOf(),
    val useRandomCornerPointsColor: Boolean = true,
    val useCornerPointsGlowEffect: Boolean = true,
    val cornerPointsColorChangeSpeedMs: Int = 500,
    val cornerPointsRadiusDp: Dp = 4.dp
) : Serializable
```

</details>

<details>
  <summary>Center Text Style</summary>
  </br>

```kotlin
/**
 * @param text is center text value
 * @param textColor
 * * is color of center text
 * - if [textColor] size = 1 then gradient text color disable and text color will be value of `textColor[0]`
 * - if [textColor] size > 1 then gradient text color enable
 * - if [textColor] is empty then gradient text color disable and text color will be [Color.Black]
 * @param textSizeSp is size of center text
 * @param letterSpacingSp is letter spacing of center text
 * @param textFontId is custom font resource id of center text
 */
data class CenterTextStyle(
    val text: String,
    val textColor: List<Color> = listOf(Color.Black),
    val textSizeSp: TextUnit = 16.sp,
    val letterSpacingSp: TextUnit = TextUnit.Unspecified,
    @FontRes val textFontId: Int? = null
) : Serializable
```

</details>

<details>
  <summary>Center Image Style</summary>
  </br>

```kotlin
/**
 * @param image is [ImageBitmap] instance of center image
 * @param imageColor is center image tint color
 */
data class CenterImageStyle(
    val image: ImageBitmap? = null,
    val imageColor: Color = Color.Unspecified
) : Serializable
```

</details>

<details>
  <summary>Item Separator Style</summary>
  </br>

```kotlin
/**
 * @param drawItemSeparator is enable or disable wheel item separator drawing
 * @param itemSeparatorColor
 * * is color of item separator line
 * - if [itemSeparatorColor] size = 1 then gradient separator color disable and separator color will be value of `itemSeparatorColor[0]`
 * - if [itemSeparatorColor] size > 1 then gradient separator color enable
 * - if [itemSeparatorColor] is empty then gradient separator color disable and separator color will be [Color.Black]
 * @param itemSeparatorThicknessDp is thickness of item separator line
 */
data class ItemSeparatorStyle(
    val drawItemSeparator: Boolean = false,
    val itemSeparatorColor: List<Color> = listOf(Color.Black),
    val itemSeparatorThicknessDp: Dp = 4.dp
) : Serializable

```

</details>

<details>
  <summary>Item Text Style</summary>
  </br>

```kotlin
/**
 * @param textOrientation is text orientation of wheel items [TextOrientation.HORIZONTAL] or [TextOrientation.VERTICAL]
 * @param textPaddingDp is text padding from wheel corner
 * @param textSizeSp is text size of wheel items
 * @param letterSpacingSp is letter spacing of wheel items text
 * @param textFontId is custom font resource id of wheel items text
 */
data class ItemTextStyle(
    val textOrientation: TextOrientation = TextOrientation.HORIZONTAL,
    val textPaddingDp: Dp = 4.dp,
    val textSizeSp: TextUnit = 16.sp,
    val letterSpacingSp: TextUnit = TextUnit.Unspecified,
    @FontRes val textFontId: Int? = null
) : Serializable
```

</details>

<details>
  <summary>Rotate Style</summary>
  </br>

```kotlin
/**
 * @param rotateTimeMs is wheel rotate duration
 * @param rotateSpeed is wheel rotate speed [RotationSpeed.FAST], [RotationSpeed.NORMAL] or [RotationSpeed.SLOW]
 * @param rotateSpeedMultiplier is wheel rotate speed multiplier
 * @param stopCenterOfItem
 * * if `true` the arrow points to the center of the slice
 * - if `false` the arrow points to a random point on the slice.
 * @param rotationDirection is wheel rotate direction [RotationDirection.CLOCKWISE], [RotationDirection.COUNTER_CLOCKWISE]
 */
data class RotateStyle(
    val rotateTimeMs: Int = 5000,
    val rotateSpeed: RotationSpeed = RotationSpeed.NORMAL,
    val rotateSpeedMultiplier: Float = 1F,
    val stopCenterOfItem: Boolean = false,
    val rotationDirection: RotationDirection = RotationDirection.CLOCKWISE
) : Serializable
```

</details>

<details>
  <summary>Rotate Via Swipe Style</summary>
  </br>

```kotlin
/**
 * @param rotateViaSwipe is enable or disable start wheel rotate via swipe down
 * @param rotateSwipeDistanceDp is swipe distance to start rotate wheel
 * @param allowRotateAgain is enable or disable allowing another rotate after first rotation completed
 */
data class RotateViaSwipeStyle(
  val rotateViaSwipe: Boolean = false,
  val rotateSwipeDistanceDp: Dp = 25.dp,
  val allowRotateAgain: Boolean = false
) : Serializable

```

</details>

<details>
  <summary>Stroke Style</summary>
  </br>

```kotlin
/**
 * @param drawStroke is enable or disable wheel corner stroke drawing
 * @param strokeColor
 * * * is color of stroke line
 *  * - if [strokeColor] size = 1 then gradient stroke color disable and stroke color will be value of `strokeColor[0]`
 *  * - if [strokeColor] size > 1 then gradient stroke color enable
 *  * - if [strokeColor] is empty then gradient stroke color disable and stroke color will be [Color.Black]
 * @param strokeThicknessDp is thickness of item stroke circle
 */
data class StrokeStyle(
    val drawStroke: Boolean = false,
    val strokeColor: List<Color> = listOf(Color.Black),
    val strokeThicknessDp: Dp = 10.dp
) : Serializable

```

</details>


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- Support -->

## Support

Having amazing people like you behind me is a huge motivation to keep pushing forward and improving
my work. 💪

If you like the work that I do, you can help and support me by buying a cup of coffee. ☕️

[!["Buy Me A Coffee"][buy-me-a-coffee-shield]][buy-me-a-coffee-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and
create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull
request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- LICENSE -->

## License

Distributed under the [Apache 2.0 License](LICENSE). See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTACT -->

## Contact

<p align="center">
  <b>Made with ❤️ by Caner YILMAZ</b><br>
  <a href="mailto:caneryilmaz.apps@gmail.com">caneryilmaz.apps@gmail.com</a><br><br>
  <a>
    <img src="https://avatars.githubusercontent.com/u/32595397?s=400&u=2f3051570d7ad9d65304f34eba943012f380433d&v=4" width="200">
  </a>
</p>

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/caneryilmaz52/LuckyWheelViewCompose?style=for-the-badge

[contributors-url]: https://github.com/caneryilmaz52/LuckyWheelViewCompose/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/caneryilmaz52/LuckyWheelViewCompose?style=for-the-badge

[forks-url]: https://github.com/caneryilmaz52/LuckyWheelViewCompose/network/members

[stars-shield]: https://img.shields.io/github/stars/caneryilmaz52/LuckyWheelViewCompose?style=for-the-badge

[stars-url]: https://github.com/caneryilmaz52/LuckyWheelViewCompose/stargazers

[issues-shield]: https://img.shields.io/github/issues/caneryilmaz52/LuckyWheelViewCompose?style=for-the-badge

[issues-url]: https://github.com/caneryilmaz52/LuckyWheelViewCompose/issues

[license-shield]: https://img.shields.io/github/license/caneryilmaz52/LuckyWheelViewCompose?style=for-the-badge

[license-url]: https://github.com/caneryilmaz52/LuckyWheelViewCompose/blob/master/LICENSE

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://linkedin.com/in/caneryilmaz52

[jitpack-url]: https://jitpack.io/#caneryilmaz52/LuckyWheelViewCompose

[jitpack-version-shield]: https://jitpack.io/v/caneryilmaz52/LuckyWheelViewCompose.svg

[jitpack-download-week-shield]: https://jitpack.io/v/caneryilmaz52/LuckyWheelViewCompose/week.svg

[jitpack-download-month-shield]: https://jitpack.io/v/caneryilmaz52/LuckyWheelViewCompose/month.svg

[jitpack-url]: https://jitpack.io/#caneryilmaz52/LuckyWheelViewCompose

[android-weekly-shield]: https://androidweekly.net/issues/issue-629/badge

[android-weekly-url]: https://androidweekly.net/issues/issue-629

[buy-me-a-coffee-shield]: https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png

[buy-me-a-coffee-url]: https://www.buymeacoffee.com/caneryilmaz