package com.mozzartbet.greekkeno.feature.game.common.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.floor

@Composable
fun BallsTable(
    modifier: Modifier = Modifier,
    hasBorder: Boolean = false,
    textElementSize: Dp = 14.dp,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    numberOfColumns: Int = 10,
    selectedBalls: List<Int> = emptyList(),
    balls: List<Int> = selectedBalls,
    onBallClick: (Int) -> Unit = { }
) {
    val numberOfRows = getNumberOfRows(numberOfColumns, balls.size)

    Column(modifier.fillMaxWidth()) {
        repeat(numberOfRows) { i ->
            Row {
                repeat(numberOfColumns) { j ->

                    val ballsIndex = i * numberOfColumns + j

                    val ball = if (ballsIndex < balls.size) balls[i * numberOfColumns + j] else 0

                    val borderColor = if (selectedBalls.contains(ball)) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        Color.Transparent
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 0.5.dp,
                                color = if (hasBorder) {
                                    MaterialTheme.colorScheme.onBackground
                                } else {
                                    Color.Transparent
                                }
                            )
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clickable {
                                onBallClick(ball)
                            }
                    ) {
                        if (ballsIndex < balls.size) {
                            Text(
                                modifier = Modifier
                                    .size(textElementSize)
                                    .align(Alignment.Center)
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                                    .drawBehind {
                                        drawCircle(
                                            color = borderColor,
                                            radius = this.size.maxDimension,
                                            style = Stroke(
                                                width = 2.dp.toPx()
                                            )
                                        )
                                    },
                                text = "$ball",
                                textAlign = TextAlign.Center,
                                style = textStyle
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun getNumberOfRows(numberOfColumns: Int, numberOfElements: Int): Int {
    val elements = numberOfElements.toDouble() - 1
    val result = floor(elements / numberOfColumns) + 1

    return result.toInt()
}

@Preview(device = "spec:width=1080px,height=2340px,dpi=440")
@Composable
fun BallsTablePreview() {
    Surface {
        BallsTable(numberOfColumns = 10, selectedBalls = listOf(7, 2, 3, 4, 14, 99)) {}
    }
}

@Preview(device = "spec:width=1080px,height=2340px,dpi=440")
@Composable
fun BallsTablePreview2() {
    Surface {
        BallsTable(
            numberOfColumns = 6,
            selectedBalls = listOf(7, 2, 3, 4, 14, 99),
            textElementSize = 20.dp,
            textStyle = MaterialTheme.typography.labelLarge
        ) {}
    }
}

@Preview(device = "spec:width=1080px,height=2340px,dpi=440")
@Composable
fun BallsTablePreview3() {
    Surface {
        BallsTable(
            numberOfColumns = 10,
            balls = listOf(7, 2, 3, 4, 14, 99),
            selectedBalls = listOf(14)
        ) {}
    }
}

@Preview(device = "spec:width=1080px,height=2340px,dpi=440")
@Composable
fun BallsTablePreview4() {
    val balls = mutableListOf<Int>()
    repeat(80) { i ->
        balls.add(i + 1)
    }

    Surface {
        BallsTable(numberOfColumns = 10, balls = balls, selectedBalls = listOf(14, 79)) {}
    }
}
