package com.caneryilmaz.apps.luckywheelviewcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.caneryilmaz.apps.luckywheel.compose.LuckyWheelView
import com.caneryilmaz.apps.luckywheel.compose.constant.RotationStatus
import com.caneryilmaz.apps.luckywheel.compose.constant.TextOrientation
import com.caneryilmaz.apps.luckywheel.compose.data.CornerPointsStyle
import com.caneryilmaz.apps.luckywheel.compose.data.ItemSeparatorStyle
import com.caneryilmaz.apps.luckywheel.compose.data.ItemTextStyle
import com.caneryilmaz.apps.luckywheel.compose.data.StrokeStyle
import com.caneryilmaz.apps.luckywheel.compose.data.WheelData
import com.caneryilmaz.apps.luckywheel.compose.state.WheelViewState
import com.caneryilmaz.apps.luckywheelviewcompose.ui.theme.LuckyWheelViewComposeTheme

val backgroundColorList = listOf(
    Color(0, 255, 255),
    Color(0, 188, 212),
    Color(244, 67, 54),
    Color(156, 39, 176),
    Color(255, 87, 34),
    Color(233, 30, 99),
    Color(76, 175, 80),
    Color(255, 193, 7)
)

val textColorList = listOf(
    Color(0, 0, 0),
    Color(255, 255, 255),
    Color(255, 0, 0),
    Color(0, 255, 0),
    Color(0, 0, 255),
    Color(0, 255, 255),
    Color(255, 0, 255),
    Color(255, 255, 0)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LuckyWheelViewComposeTheme {
                val dummyWheelData = ArrayList<WheelData>()

                val imageBitmap: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.star_32dp)

                (0..7).forEach {
                    val item = WheelData(
                        text = "Item\n#${it + 1}",
                        textColor = listOf(textColorList.random(), textColorList.random()),
                        backgroundColor = listOf(backgroundColorList.random(), backgroundColorList.random()),
                        icon = imageBitmap
                    )
                    dummyWheelData.add(item)
                }
                LuckyWheelViewComposeTheme {
                    LuckyWheel(wheelItems = dummyWheelData)
                }

                LuckyWheel(dummyWheelData)
            }
        }
    }
}

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
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        LuckyWheelView(
            modifier = Modifier
                .width(350.dp)
                .height(350.dp)
                .padding(vertical = 30.dp),
            wheelItems = wheelItems,
            itemSeparatorStyle = ItemSeparatorStyle(
                drawItemSeparator = true,
                itemSeparatorThicknessDp = 2.dp,
                itemSeparatorColor = listOf(
                    backgroundColorList.random(),
                    backgroundColorList.random()
                )
            ),
            strokeStyle = StrokeStyle(
                drawStroke = true,
                strokeColor = listOf(backgroundColorList.random()),
                strokeThicknessDp = 16.dp
            ),
            cornerPointsStyle = CornerPointsStyle(drawCornerPoints = true),
            itemTextStyle = ItemTextStyle(textOrientation = TextOrientation.HORIZONTAL),
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val dummyWheelData = ArrayList<WheelData>()

    val imageBitmap: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.star_32dp)

    (0..7).forEach {
        val item = WheelData(
            text = "Item\n#${it + 1}",
            textColor = listOf(textColorList.random(), textColorList.random()),
            backgroundColor = listOf(backgroundColorList.random(), backgroundColorList.random()),
            icon = imageBitmap
        )
        dummyWheelData.add(item)
    }
    LuckyWheelViewComposeTheme {
        LuckyWheel(wheelItems = dummyWheelData)
    }
}