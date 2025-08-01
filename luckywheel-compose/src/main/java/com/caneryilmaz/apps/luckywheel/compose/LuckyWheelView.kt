package com.caneryilmaz.apps.luckywheel.compose

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.Typeface
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.graphics.scale
import androidx.core.graphics.withSave
import androidx.core.graphics.withTranslation
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.toBitmap
import com.caneryilmaz.apps.luckywheel.compose.constant.ArrowPosition
import com.caneryilmaz.apps.luckywheel.compose.constant.RotationDirection
import com.caneryilmaz.apps.luckywheel.compose.constant.RotationSpeed
import com.caneryilmaz.apps.luckywheel.compose.constant.RotationStatus
import com.caneryilmaz.apps.luckywheel.compose.constant.TextOrientation
import com.caneryilmaz.apps.luckywheel.compose.data.ArrowStyle
import com.caneryilmaz.apps.luckywheel.compose.data.CenterImageStyle
import com.caneryilmaz.apps.luckywheel.compose.data.CenterPointStyle
import com.caneryilmaz.apps.luckywheel.compose.data.CenterTextStyle
import com.caneryilmaz.apps.luckywheel.compose.data.CornerPointsStyle
import com.caneryilmaz.apps.luckywheel.compose.data.ItemIconStyle
import com.caneryilmaz.apps.luckywheel.compose.data.ItemSeparatorStyle
import com.caneryilmaz.apps.luckywheel.compose.data.ItemTextStyle
import com.caneryilmaz.apps.luckywheel.compose.data.RandomRotateStyle
import com.caneryilmaz.apps.luckywheel.compose.data.RotateStyle
import com.caneryilmaz.apps.luckywheel.compose.data.RotateViaSwipeStyle
import com.caneryilmaz.apps.luckywheel.compose.data.StrokeStyle
import com.caneryilmaz.apps.luckywheel.compose.data.WheelData
import com.caneryilmaz.apps.luckywheel.compose.state.WheelViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
import kotlin.random.Random

/**
 * @param modifier the Modifier to be applied to this WheelView
 *
 * @param wheelItems is a MutableList of [WheelData] check info for [WheelData] description
 *
 * @param target
 * * is index of the item to win
 * - [target] must be between 0 and wheelItems last index (exclusive)
 * - also if target a negative number then throw IllegalArgumentException
 * - also if target bigger than [wheelItems] last index then throw IndexOutOfBoundsException
 *
 * @param randomRotateStyle check info for [RandomRotateStyle] description
 *
 * @param itemIconStyle check info for [ItemIconStyle] description
 *
 * @param arrowStyle check info for [ArrowStyle] description
 *
 * @param centerPointStyle check info for [CenterPointStyle] description
 *
 * @param cornerPointsStyle check info for [CornerPointsStyle] description
 *
 * @param centerTextStyle check info for [CenterTextStyle] description
 *
 * @param centerImageStyle check info for [CenterImageStyle] description
 *
 * @param itemSeparatorStyle check info for [ItemSeparatorStyle] description
 *
 * @param itemTextStyle check info for [ItemTextStyle] description
 *
 * @param rotateStyle check info for [RotateStyle] description
 *
 * @param rotateViaSwipeStyle check info for [RotateViaSwipeStyle] description
 *
 * @param strokeStyle check info for [StrokeStyle] description
 *
 * @param onRotationComplete is invoke when wheel stop. return `wheelItems[target]` value
 *
 * @param onRotationStatus is invoke when wheel rotation status change. return current wheel [RotationStatus] value
 *
 * @param wheelViewState check info for [WheelViewState] description
 */
@Composable
fun LuckyWheelView(
    modifier: Modifier,
    wheelItems: List<WheelData>,
    target: Int = 0,
    randomRotateStyle: RandomRotateStyle = RandomRotateStyle(),
    itemIconStyle: ItemIconStyle = ItemIconStyle(),
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
) {
    val currentContext = LocalContext.current
    val wheelRotationAnim = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    var startRotateViaSwipe by remember { mutableStateOf(WheelViewState(startRotate = false)) }

    var winTarget: Int
    var wheelRadius: Float
    var wheelStrokeRadius: Float
    var wheelSize: Size
    var startAngle: Float = remember { 0F }
    var sweepAngle: Float = remember { 0F }

    var textPaint: Paint

    val pointsOnCircle = wheelItems.size + (wheelItems.size * cornerPointsStyle.cornerPointsEachSlice)

    val wheelPointColors = remember {
        mutableStateListOf(*Array(pointsOnCircle) { Color.Black })
    }

    var arrowSwingAnim by remember { mutableFloatStateOf(0F) }
    var arrowSwingAnimDuration by remember { mutableFloatStateOf(arrowStyle.arrowSwingDurationMs.toFloat()) }

    LaunchedEffect(Unit) {
        while (true) {
            if (cornerPointsStyle.useRandomCornerPointsColor || cornerPointsStyle.cornerPointsColor.isEmpty()) {
                wheelPointColors.indices.forEach { i ->
                    wheelPointColors[i] = Color(
                        red = (0..255).random() / 255f,
                        green = (0..255).random() / 255f,
                        blue = (0..255).random() / 255f
                    )
                }
            } else {
                wheelPointColors.indices.forEach { i ->
                    wheelPointColors[i] = cornerPointsStyle.cornerPointsColor[(0..<cornerPointsStyle.cornerPointsColor.size).random()]
                }
            }
            delay(timeMillis = cornerPointsStyle.cornerPointsColorChangeSpeedMs.toLong())
        }
    }

    LaunchedEffect(key1 = wheelViewState, key2 = startRotateViaSwipe) {
        if (wheelViewState.startRotate || startRotateViaSwipe.startRotate) {

            winTarget = if (randomRotateStyle.rotateToRandomTarget) {
                randomRotateStyle.randomTargets?.randomOrNull() ?: Random.nextInt(0, wheelItems.size)
            } else {
                target
            }

            if (winTarget < 0) {
                throw IllegalArgumentException("WheelView target must be bigger than 0 (zero). Provided target: $winTarget")
            } else if (winTarget > wheelItems.size - 1) {
                throw IndexOutOfBoundsException("WheelView target must be between 0 and wheelItems last index ${wheelItems.size - 1} (exclusive). Provided target: $winTarget")
            } else {
                wheelRotationAnim.animateTo(
                    targetValue = 0F,
                    animationSpec = tween(
                        durationMillis = 0
                    )
                ) {
                    onRotationStatus.invoke(RotationStatus.IDLE)
                    arrowSwingAnim = 0F
                    arrowSwingAnimDuration = arrowStyle.arrowSwingDurationMs.toFloat()
                }

                val rotationSpeed = when (rotateStyle.rotateSpeed) {
                    RotationSpeed.FAST -> 15 * rotateStyle.rotateSpeedMultiplier
                    RotationSpeed.NORMAL -> 10 * rotateStyle.rotateSpeedMultiplier
                    RotationSpeed.SLOW -> 5 * rotateStyle.rotateSpeedMultiplier
                }

                val halfOfWheelItem: Float = sweepAngle / 2
                val targetItemAngle: Float = sweepAngle * (winTarget + 1)

                val rotationTargetValue = when (rotateStyle.rotationDirection) {
                    RotationDirection.CLOCKWISE -> {
                        val rotationAngleOfTarget = if (rotateStyle.stopCenterOfItem) {
                            270 - targetItemAngle + halfOfWheelItem
                        } else {
                            val maxRange: Int = sweepAngle.toInt() - 1
                            val stopPosition = Random.nextInt(1, maxRange)
                            270 - targetItemAngle + stopPosition
                        }

                        (360 * rotationSpeed) + rotationAngleOfTarget
                    }
                    RotationDirection.COUNTER_CLOCKWISE -> {
                        val rotationAngleOfTarget = if (rotateStyle.stopCenterOfItem) {
                            -270 + targetItemAngle - halfOfWheelItem
                        } else {
                            val maxRange: Int = sweepAngle.toInt() - 1
                            val stopPosition = Random.nextInt(1, maxRange)
                            -270 + targetItemAngle - stopPosition
                        }

                        -((360 * rotationSpeed) + rotationAngleOfTarget)
                    }
                }
                coroutineScope.launch {
                    wheelRotationAnim.animateTo(
                        targetValue = rotationTargetValue,
                        animationSpec = tween(
                            durationMillis = rotateStyle.rotateTimeMs,
                            easing = FastOutSlowInEasing
                        )
                    ) {
                        if (value == targetValue) {
                            onRotationComplete.invoke(wheelItems[winTarget])

                            if (rotateViaSwipeStyle.allowRotateAgain) {
                                startRotateViaSwipe = WheelViewState(startRotate = false)
                            }
                        } else {
                            onRotationStatus.invoke(RotationStatus.ROTATING)
                        }
                    }
                }.invokeOnCompletion {
                    when(wheelRotationAnim.value) {
                        wheelRotationAnim.targetValue -> {
                            onRotationStatus.invoke(RotationStatus.COMPLETED)
                        }
                        0F -> {
                            onRotationStatus.invoke(RotationStatus.IDLE)
                        }
                        else -> {
                            onRotationStatus.invoke(RotationStatus.CANCELED)
                            if (rotateViaSwipeStyle.allowRotateAgain) {
                                startRotateViaSwipe = WheelViewState(startRotate = false)
                            }
                        }
                    }

                    arrowSwingAnim = 0F
                    arrowSwingAnimDuration = arrowStyle.arrowSwingDurationMs.toFloat()
                }

                if (arrowStyle.arrowAnimationStatus) {
                    delay(350)
                    val startTime = System.currentTimeMillis()
                    while (System.currentTimeMillis() - startTime < (rotateStyle.rotateTimeMs - 350)) {
                        arrowSwingAnim = when (arrowSwingAnim) {
                            0F -> {
                                arrowStyle.arrowSwingDistanceDp.value
                            }
                            -arrowStyle.arrowSwingDistanceDp.value -> {
                                arrowStyle.arrowSwingDistanceDp.value
                            }
                            else -> {
                                -arrowStyle.arrowSwingDistanceDp.value
                            }
                        }
                        delay(arrowSwingAnimDuration.toLong())
                        if (System.currentTimeMillis() - startTime >= rotateStyle.rotateTimeMs / 2) {
                            arrowSwingAnimDuration += (arrowSwingAnimDuration * arrowStyle.arrowSwingSlowdownMultiplier)
                        }
                    }
                }
            }
        } else {
            onRotationStatus.invoke(RotationStatus.IDLE)
            arrowSwingAnim = 0F
            arrowSwingAnimDuration = arrowStyle.arrowSwingDurationMs.toFloat()
        }
    }

    Box(modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {

        val textTypeface = if (itemTextStyle.textFontId == null) {
            Typeface.SANS_SERIF
        } else {
            ResourcesCompat.getFont(currentContext, itemTextStyle.textFontId) ?: Typeface.SANS_SERIF
        }

        Box(modifier = Modifier.pointerInput(Unit) {
            detectVerticalDragGestures(onVerticalDrag = { _, dragAmount ->
                if (rotateViaSwipeStyle.rotateViaSwipe) {
                    if (dragAmount > rotateViaSwipeStyle.rotateSwipeDistanceDp.value) {
                        startRotateViaSwipe = WheelViewState(startRotate = true)
                    }
                }
            })
        }) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                rotate(wheelRotationAnim.value, pivot = center) {
                    var wheelStrokeThicknessF = 0f

                    if (strokeStyle.drawStroke) {
                        wheelStrokeThicknessF = strokeStyle.strokeThicknessDp.toPx()

                        wheelStrokeRadius = (size.minDimension - wheelStrokeThicknessF) / 2F

                        val strokeBrush = if (strokeStyle.strokeColor.size == 1) {
                            Brush.linearGradient(
                                colors = listOf(strokeStyle.strokeColor[0], strokeStyle.strokeColor[0])
                            )
                        } else if (strokeStyle.strokeColor.isEmpty()) {
                            Brush.linearGradient(
                                colors = listOf(Color.Black, Color.Black)
                            )
                        } else {
                            Brush.linearGradient(
                                colors = strokeStyle.strokeColor
                            )
                        }

                        drawCircle(
                            brush = strokeBrush,
                            radius = wheelStrokeRadius,
                            center = center,
                            style = Stroke(width = wheelStrokeThicknessF)
                        )
                    }

                    wheelRadius = (size.minDimension - wheelStrokeThicknessF) / 2F
                    wheelSize = Size(
                        width = size.minDimension - wheelStrokeThicknessF,
                        height = size.minDimension- wheelStrokeThicknessF
                    )

                    drawCircle(color = Color.Black, radius = wheelRadius, center = center)

                    if (wheelItems.isNotEmpty()) {
                        sweepAngle = 360F / wheelItems.size

                        textPaint = Paint().apply {
                            typeface = textTypeface
                            isAntiAlias = true
                            isDither = true
                            textSize = itemTextStyle.textSizeSp.toPx()
                            textAlign = Paint.Align.CENTER
                            if (itemTextStyle.letterSpacingSp != TextUnit.Unspecified) {
                                letterSpacing = itemTextStyle.letterSpacingSp.toPx()
                            }
                        }

                        wheelItems.forEach { item ->
                            val backgroundBrush = if (item.backgroundColor.size == 1) {
                                Brush.radialGradient(
                                    colors = listOf(item.backgroundColor[0], item.backgroundColor[0])
                                )
                            } else if(item.backgroundColor.isEmpty()) {
                                throw IllegalArgumentException("At least one color value is required: backgroundColor list is empty.")
                            } else {
                                Brush.radialGradient(
                                    colors = item.backgroundColor
                                )
                            }

                            drawArc(
                                brush = backgroundBrush,
                                startAngle = startAngle,
                                sweepAngle = sweepAngle,
                                useCenter = true,
                                topLeft = Offset(x = center.x - wheelRadius, y = center.y - wheelRadius),
                                size = wheelSize
                            )

                            val path = Path().apply {
                                addArc(
                                    oval = androidx.compose.ui.geometry.Rect(
                                        center = Offset(x = center.x, y = center.y),
                                        radius = wheelRadius - textPaint.textSize - itemTextStyle.textPaddingDp.toPx()
                                    ),
                                    startAngleDegrees = startAngle,
                                    sweepAngleDegrees = sweepAngle
                                )
                            }

                            val textPaintShader = if (item.textColor.size == 1) {
                                LinearGradient(
                                    path.getBounds().centerLeft.x,
                                    path.getBounds().centerLeft.y,
                                    path.getBounds().centerRight.x,
                                    path.getBounds().centerRight.y,
                                    intArrayOf(
                                        item.textColor[0].toArgb(), item.textColor[0].toArgb(),
                                    ),
                                    null,
                                    Shader.TileMode.CLAMP
                                )
                            } else if (item.textColor.isEmpty()) {
                                throw IllegalArgumentException("At least one color value is required: textColor list is empty.")
                            } else {
                                LinearGradient(
                                    path.getBounds().centerLeft.x,
                                    path.getBounds().centerLeft.y,
                                    path.getBounds().centerRight.x,
                                    path.getBounds().centerRight.y,
                                    item.textColor.map { it.toArgb() }.toIntArray(),
                                    null,
                                    Shader.TileMode.CLAMP
                                )
                            }
                            textPaint.shader = textPaintShader

                            val itemTextTypeface = if (item.textFontId == null) {
                                textTypeface
                            } else {
                                ResourcesCompat.getFont(currentContext, item.textFontId) ?: textTypeface
                            }
                            textPaint.typeface = itemTextTypeface


                            val nativeCanvas = drawContext.canvas.nativeCanvas

                            when (itemTextStyle.textOrientation) {
                                TextOrientation.HORIZONTAL -> {
                                    val separatedText = item.text.split("\n")

                                    val horizontalOffset = (((wheelRadius * Math.PI) / wheelRadius)).toFloat()
                                    val verticalOffset = ((wheelRadius / 2 / 3) - 75)

                                    if (separatedText.size > 1) {
                                        separatedText.forEachIndexed { lineIndex, lineText ->
                                            nativeCanvas.drawTextOnPath(
                                                lineText,
                                                path.asAndroidPath(),
                                                horizontalOffset,
                                                verticalOffset + ((textPaint.textSize + textPaint.letterSpacing) * lineIndex),
                                                textPaint
                                            )
                                        }
                                    } else {
                                        nativeCanvas.drawTextOnPath(
                                            item.text,
                                            path.asAndroidPath(),
                                            horizontalOffset,
                                            verticalOffset,
                                            textPaint
                                        )
                                    }
                                }

                                TextOrientation.VERTICAL -> {
                                    val textArray = item.text.toCharArray()

                                    val horizontalOffset = (((wheelRadius * Math.PI) / wheelRadius)).toFloat()
                                    val verticalOffset = ((wheelRadius / 2 / 3) - 75)

                                    textArray.forEachIndexed { index, char ->
                                        nativeCanvas.drawTextOnPath(
                                            char.toString(),
                                            path.asAndroidPath(),
                                            horizontalOffset,
                                            verticalOffset + (index * itemTextStyle.textSizeSp.toPx()),
                                            textPaint
                                        )
                                    }
                                }

                                TextOrientation.VERTICAL_TO_CENTER -> {
                                    val middleAngle = startAngle + sweepAngle / 2
                                    val middleAngleRad = Math.toRadians(middleAngle.toDouble()).toFloat()
                                    val textRadius = wheelRadius * itemTextStyle.textPositionFraction

                                    val x = center.x + textRadius * cos(middleAngleRad)
                                    val y = center.y + textRadius * sin(middleAngleRad)

                                    nativeCanvas.withSave {
                                        val rotationAngle = middleAngle + 180

                                        rotate(rotationAngle, x, y)

                                        drawText(item.text, x, y + (textPaint.textSize / 3), textPaint)
                                    }
                                }

                                TextOrientation.VERTICAL_TO_CORNER -> {
                                    val middleAngle = startAngle + sweepAngle / 2
                                    val middleAngleRad = Math.toRadians(middleAngle.toDouble()).toFloat()
                                    val textRadius = wheelRadius * itemTextStyle.textPositionFraction

                                    val x = center.x + textRadius * cos(middleAngleRad)
                                    val y = center.y + textRadius * sin(middleAngleRad)

                                    nativeCanvas.withSave {
                                        rotate(middleAngle, x, y)

                                        drawText(item.text, x, y + (textPaint.textSize / 3), textPaint)
                                    }
                                }
                            }

                            if (item.iconURL != null) {
                                val request = ImageRequest.Builder(currentContext)
                                    .data(item.iconURL)
                                    .target(
                                        onSuccess = { result ->
                                            val adaptiveSize = (100 * itemIconStyle.iconSizeMultiplier).toInt()
                                            val scaledIcon = result.toBitmap().scale(adaptiveSize, adaptiveSize)
                                            val imageBitmap = scaledIcon.asImageBitmap()

                                            val imageWidth = imageBitmap.width.toFloat()
                                            val imageHeight = imageBitmap.height.toFloat()
                                            val angle = sweepAngle * wheelItems.indexOf(item) + sweepAngle / 2
                                            val radians = Math.toRadians(angle.toDouble())

                                            val sliceCenterX = (center.x + (wheelRadius * itemIconStyle.iconPositionFraction) * cos(radians)).toFloat()
                                            val sliceCenterY = (center.y + (wheelRadius * itemIconStyle.iconPositionFraction) * sin(radians)).toFloat()

                                            if (item.iconColor != Color.Unspecified) {
                                                drawImage(
                                                    image = imageBitmap,
                                                    topLeft = Offset(
                                                        x = sliceCenterX - imageWidth / 2,
                                                        y = sliceCenterY - imageHeight / 2
                                                    ),
                                                    colorFilter = ColorFilter.tint(color = item.iconColor)
                                                )
                                            } else {
                                                drawImage(
                                                    image = imageBitmap,
                                                    topLeft = Offset(
                                                        x = sliceCenterX - imageWidth / 2,
                                                        y = sliceCenterY - imageHeight / 2
                                                    )
                                                )
                                            }
                                        },
                                        onError = { error ->
                                            item.icon?.let { icon ->
                                                val adaptiveSize = (100 * itemIconStyle.iconSizeMultiplier).toInt()
                                                val scaledIcon = icon.asAndroidBitmap().scale(adaptiveSize, adaptiveSize)
                                                val imageBitmap = scaledIcon.asImageBitmap()

                                                val imageWidth = imageBitmap.width.toFloat()
                                                val imageHeight = imageBitmap.height.toFloat()
                                                val angle = sweepAngle * wheelItems.indexOf(item) + sweepAngle / 2
                                                val radians = Math.toRadians(angle.toDouble())

                                                val sliceCenterX = (center.x + (wheelRadius * itemIconStyle.iconPositionFraction) * cos(radians)).toFloat()
                                                val sliceCenterY = (center.y + (wheelRadius * itemIconStyle.iconPositionFraction) * sin(radians)).toFloat()

                                                if (item.iconColor != Color.Unspecified) {
                                                    drawImage(
                                                        image = imageBitmap,
                                                        topLeft = Offset(
                                                            x = sliceCenterX - imageWidth / 2,
                                                            y = sliceCenterY - imageHeight / 2
                                                        ),
                                                        colorFilter = ColorFilter.tint(color = item.iconColor)
                                                    )
                                                } else {
                                                    drawImage(
                                                        image = imageBitmap,
                                                        topLeft = Offset(
                                                            x = sliceCenterX - imageWidth / 2,
                                                            y = sliceCenterY - imageHeight / 2
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    )
                                    .build()
                                currentContext.imageLoader.enqueue(request)
                            } else {
                                item.icon?.let { icon ->
                                    val adaptiveSize = (100 * itemIconStyle.iconSizeMultiplier).toInt()
                                    val scaledIcon = icon.asAndroidBitmap().scale(adaptiveSize, adaptiveSize)
                                    val imageBitmap = scaledIcon.asImageBitmap()

                                    val imageWidth = max(imageBitmap.width, imageBitmap.height)
                                    val angle = sweepAngle * wheelItems.indexOf(item).toFloat() + sweepAngle / 2
                                    val radians = Math.toRadians(angle.toDouble())

                                    val sliceCenterX = (center.x + (wheelRadius * itemIconStyle.iconPositionFraction) * cos(radians)).toFloat()
                                    val sliceCenterY = (center.y + (wheelRadius * itemIconStyle.iconPositionFraction) * sin(radians)).toFloat()

                                    nativeCanvas.withTranslation(sliceCenterX, sliceCenterY) {
                                        rotate(angle + 90F)

                                        val rect = Rect(
                                            -imageWidth / 2,
                                            -imageWidth / 2,
                                            imageWidth / 2,
                                            imageWidth / 2
                                        )

                                        if (item.iconColor != Color.Unspecified) {
                                            val paint = Paint()
                                            paint.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(item.iconColor.toArgb(), BlendModeCompat.SRC_IN)
                                            paint.color = item.iconColor.toArgb()
                                            nativeCanvas.drawBitmap(
                                                imageBitmap.asAndroidBitmap(),
                                                null,
                                                rect,
                                                paint
                                            )
                                        } else {
                                            nativeCanvas.drawBitmap(
                                                imageBitmap.asAndroidBitmap(),
                                                null,
                                                rect,
                                                null
                                            )
                                        }
                                    }
                                }
                            }

                            startAngle += sweepAngle
                        }

                        if (itemSeparatorStyle.drawItemSeparator) {
                            val itemSeparatorBrush = if (itemSeparatorStyle.itemSeparatorColor.size == 1) {
                                Brush.radialGradient(
                                    colors = listOf(itemSeparatorStyle.itemSeparatorColor[0], itemSeparatorStyle.itemSeparatorColor[0])
                                )
                            } else if (itemSeparatorStyle.itemSeparatorColor.isEmpty()) {
                                Brush.radialGradient(
                                    colors = listOf(Color.Black, Color.Black)
                                )
                            } else {
                                Brush.radialGradient(
                                    colors = itemSeparatorStyle.itemSeparatorColor
                                )
                            }

                            wheelItems.forEachIndexed { index, _ ->
                                val angle = index * (360F / wheelItems.size)
                                val radians = Math.toRadians(angle.toDouble())
                                val x = center.x + wheelRadius * cos(radians).toFloat()
                                val y = center.y + wheelRadius * sin(radians).toFloat()
                                drawLine(
                                    brush = itemSeparatorBrush,
                                    start = center,
                                    end = Offset(x = x, y = y),
                                    strokeWidth = itemSeparatorStyle.itemSeparatorThicknessDp.toPx(),
                                    cap = StrokeCap.Butt
                                )
                            }
                        }
                    }

                    if (cornerPointsStyle.drawCornerPoints) {
                        val wheelPointsRadius = if (wheelStrokeThicknessF == 0F) {
                            ((size.minDimension - (wheelStrokeThicknessF / 2)) - (cornerPointsStyle.cornerPointsRadiusDp.toPx() * 2)) / 2F
                        } else {
                            (size.minDimension - (wheelStrokeThicknessF / 2)) / 2F
                        }
                        val angleStep = (2 * Math.PI / pointsOnCircle).toFloat()
                        for (i in 0 until pointsOnCircle) {
                            val angle = i * angleStep
                            val pointX = center.x + wheelPointsRadius * cos(angle)
                            val pointY = center.y + wheelPointsRadius * sin(angle)

                            if (cornerPointsStyle.useCornerPointsGlowEffect) {
                                drawCircle(
                                    color = wheelPointColors[i].copy(alpha = 0.3F),
                                    radius = cornerPointsStyle.cornerPointsRadiusDp.toPx() * 1.5F,
                                    center = Offset(pointX, pointY)
                                )
                            }

                            drawCircle(
                                color = wheelPointColors[i],
                                radius = cornerPointsStyle.cornerPointsRadiusDp.toPx(),
                                center = Offset(pointX, pointY)
                            )
                        }
                    }

                    if (centerPointStyle.drawCenterPoint) {
                        drawCircle(centerPointStyle.centerPointColor, centerPointStyle.centerPointRadiusDp.toPx(), center)
                    }

                    if (centerTextStyle.text != "") {
                        val centerTextTypeface = if (centerTextStyle.textFontId == null) {
                            Typeface.SANS_SERIF
                        } else {
                            ResourcesCompat.getFont(currentContext, centerTextStyle.textFontId) ?: Typeface.SANS_SERIF
                        }

                        val centerTextPaint = Paint().apply {
                            typeface = centerTextTypeface
                            isAntiAlias = true
                            isDither = true
                            textSize = centerTextStyle.textSizeSp.toPx()
                            textAlign = Paint.Align.CENTER
                            if (centerTextStyle.letterSpacingSp != TextUnit.Unspecified) {
                                letterSpacing = centerTextStyle.letterSpacingSp.toPx()
                            }
                        }

                        val textPaintShader = if (centerTextStyle.textColor.size == 1) {
                            LinearGradient(
                                center.x - centerTextPaint.measureText(centerTextStyle.text) / 2,
                                center.y - centerTextPaint.textSize / 2,
                                center.x + centerTextPaint.measureText(centerTextStyle.text) / 2,
                                center.y + centerTextPaint.textSize / 2,
                                intArrayOf(
                                    centerTextStyle.textColor[0].toArgb(), centerTextStyle.textColor[0].toArgb(),
                                ),
                                null,
                                Shader.TileMode.CLAMP
                            )
                        } else if (centerTextStyle.textColor.isEmpty()) {
                            LinearGradient(
                                center.x - centerTextPaint.measureText(centerTextStyle.text) / 2,
                                center.y - centerTextPaint.textSize / 2,
                                center.x + centerTextPaint.measureText(centerTextStyle.text) / 2,
                                center.y + centerTextPaint.textSize / 2,
                                intArrayOf(Color.Black.toArgb(), Color.Black.toArgb()),
                                null,
                                Shader.TileMode.CLAMP
                            )
                        } else {
                            LinearGradient(
                                center.x - centerTextPaint.measureText(centerTextStyle.text) / 2,
                                center.y - centerTextPaint.textSize / 2,
                                center.x + centerTextPaint.measureText(centerTextStyle.text) / 2,
                                center.y + centerTextPaint.textSize / 2,
                                centerTextStyle.textColor.map { it.toArgb() }.toIntArray(),
                                null,
                                Shader.TileMode.CLAMP
                            )
                        }
                        centerTextPaint.shader = textPaintShader

                        val separatedText = centerTextStyle.text.split("\n")
                        when (separatedText.size) {
                            2 -> {
                                drawContext.canvas.nativeCanvas.drawText(
                                    separatedText[0],
                                    center.x,
                                    center.y - 8,
                                    centerTextPaint
                                )
                                drawContext.canvas.nativeCanvas.drawText(
                                    separatedText[1],
                                    center.x,
                                    center.y - 4 + centerTextPaint.textSize,
                                    centerTextPaint
                                )
                            }
                            else -> {
                                drawContext.canvas.nativeCanvas.drawText(
                                    centerTextStyle.text,
                                    center.x,
                                    center.y + centerTextPaint.textSize / 3,
                                    centerTextPaint
                                )
                            }
                        }
                    }

                    centerImageStyle.image?.let { icon ->
                        val imageWidth = icon.width.toFloat()
                        val imageHeight = icon.height.toFloat()

                        if (centerImageStyle.imageColor != Color.Unspecified) {
                            drawImage(
                                image = icon,
                                topLeft = Offset(
                                    x = center.x - imageWidth / 2,
                                    y = center.y - imageHeight / 2
                                ),
                                colorFilter = ColorFilter.tint(color = centerImageStyle.imageColor)
                            )
                        } else {
                            drawImage(
                                image = icon,
                                topLeft = Offset(
                                    x = center.x - imageWidth / 2,
                                    y = center.y - imageHeight / 2
                                )
                            )
                        }
                    }
                }
            }

            if (arrowStyle.arrowPosition == ArrowPosition.CENTER) {
                if (arrowStyle.arrowColor != Color.Unspecified) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(size = arrowStyle.arrowSizeDp)
                            .offset(y = arrowStyle.arrowOffsetY)
                            .graphicsLayer {
                                rotationZ = if (wheelViewState.startRotate) {
                                    arrowSwingAnim
                                } else {
                                    0F
                                }
                            },
                        painter = painterResource(id = arrowStyle.arrowId),
                        colorFilter = ColorFilter.tint(color = arrowStyle.arrowColor),
                        contentDescription = ""
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(size = arrowStyle.arrowSizeDp)
                            .offset(y = arrowStyle.arrowOffsetY)
                            .graphicsLayer {
                                rotationZ = if (wheelViewState.startRotate) {
                                    arrowSwingAnim
                                } else {
                                    0F
                                }
                            },
                        painter = painterResource(id = arrowStyle.arrowId),
                        contentDescription = ""
                    )
                }
            }
        }

        if (arrowStyle.arrowPosition == ArrowPosition.TOP) {
            if (arrowStyle.arrowColor != Color.Unspecified) {
                Image(
                    modifier = Modifier
                        .size(size = arrowStyle.arrowSizeDp)
                        .offset(y = (-48).dp + arrowStyle.arrowOffsetY)
                        .graphicsLayer {
                            rotationZ = if (wheelViewState.startRotate) {
                                arrowSwingAnim
                            } else {
                                0F
                            }
                        },
                    colorFilter = ColorFilter.tint(color = arrowStyle.arrowColor),
                    painter = painterResource(id = arrowStyle.arrowId),
                    contentDescription = ""
                )
            } else {
                Image(
                    modifier = Modifier
                        .size(size = arrowStyle.arrowSizeDp)
                        .offset(y = (-48).dp + arrowStyle.arrowOffsetY)
                        .graphicsLayer {
                            rotationZ = if (wheelViewState.startRotate) {
                                arrowSwingAnim
                            } else {
                                0F
                            }
                        },
                    painter = painterResource(id = arrowStyle.arrowId),
                    contentDescription = ""
                )
            }
        }
    }
}