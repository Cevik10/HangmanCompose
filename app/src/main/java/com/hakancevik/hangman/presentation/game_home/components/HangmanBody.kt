package com.hakancevik.hangman.presentation.game_home.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.hakancevik.hangman.ui.theme.HangmanTheme

private const val SCAFFOLD_HEIGHT = 360f
private const val BEAM_LENGTH = 144f
private const val ROPE_LENGTH = 18f
private const val HEAD_RADIUS = 71f
private const val BODY_LENGTH = 144f
private const val ARM_OFFSET_FROM_HEAD = 28f
private const val ARM_LENGTH = 72f
private const val HIP_WIDTH = 36f
private const val ARM_ANGLE = 28f
private const val LEG_ANGLE = 28f
private const val HANGMAN_WIDTH_OFFSET = 2.5f
private const val HANGMAN_HEIGHT_OFFSET = 3
private const val ANIMATION_DURATION = 400
private const val STROKE_WIDTH = 8f


@Preview(showBackground = true)
@Composable
fun HangmanBodyPreview() {
    HangmanTheme(darkTheme = false) {
        HangmanBody(lives = 2)
    }
}


@SuppressLint("UnrememberedAnimatable")
@Composable
fun HangmanBody(lives: Int, bodyColor: Color = MaterialTheme.colorScheme.onBackground) {

    val animatableHead = Animatable(0f)
    val animatableBody = Animatable(0f)
    val animatableLeftArm = Animatable(0f)
    val animatableRightArm = Animatable(0f)
    val animatableLeftLeg = Animatable(0f)
    val animatableRightLeg = Animatable(0f)

    if (lives == 5)
        LaunchedEffect(animatableHead) {
            animatableHead.animateTo(
                1f,
                animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearEasing)
            )
        }

    if (lives == 4)
        LaunchedEffect(animatableBody) {
            animatableBody.animateTo(
                1f,
                animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearEasing)
            )
        }

    if (lives == 3)
        LaunchedEffect(animatableLeftArm) {
            animatableLeftArm.animateTo(
                1f,
                animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearEasing)
            )
        }

    if (lives == 2)
        LaunchedEffect(animatableRightArm) {
            animatableRightArm.animateTo(
                1f,
                animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearEasing)
            )
        }

    if (lives == 1)
        LaunchedEffect(animatableLeftLeg) {
            animatableLeftLeg.animateTo(
                1f,
                animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearEasing)
            )
        }

    if (lives == 0)
        LaunchedEffect(animatableRightLeg) {
            animatableRightLeg.animateTo(
                1f,
                animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearEasing)
            )
        }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .semantics { contentDescription = "HangmanBody" },
            onDraw = {
                drawRoundRect(
                    bodyColor,
                    topLeft = Offset(0f, 36f),
                    size = Size(size.width, size.height - 36f),
                    cornerRadius = CornerRadius(2f, 2f),
                    style = Stroke(2f)
                )
                drawLine(
                    bodyColor,
                    start = Offset(
                        size.width / HANGMAN_WIDTH_OFFSET,
                        (size.height / HANGMAN_HEIGHT_OFFSET + SCAFFOLD_HEIGHT)
                    ),
                    end = Offset(
                        size.width / HANGMAN_WIDTH_OFFSET,
                        size.height / HANGMAN_HEIGHT_OFFSET
                    ),
                    STROKE_WIDTH,
                )
                drawLine(
                    bodyColor,
                    start = Offset(
                        size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                        size.height / HANGMAN_HEIGHT_OFFSET
                    ),
                    end = Offset(
                        size.width / HANGMAN_WIDTH_OFFSET,
                        size.height / HANGMAN_HEIGHT_OFFSET
                    ),
                    STROKE_WIDTH
                )
                drawLine(
                    bodyColor,
                    start = Offset(
                        size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                        size.height / HANGMAN_HEIGHT_OFFSET
                    ),
                    end = Offset(
                        size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                        size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH
                    ),
                    STROKE_WIDTH
                )
                if (lives != 6) {
                    drawArc(
                        bodyColor,
                        startAngle = 0f,
                        sweepAngle = 360f * animatableHead.value,
                        false,
                        topLeft = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH - HEAD_RADIUS,
                            size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH
                        ),
                        size = Size(
                            HEAD_RADIUS * 2,
                            HEAD_RADIUS * 2
                        ),
                        style = Stroke(STROKE_WIDTH)
                    )
                    if (animatableHead.value == 0f && lives <= 4) {
                        drawArc(
                            bodyColor,
                            startAngle = 0f,
                            sweepAngle = 360f,
                            false,
                            topLeft = Offset(
                                size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH - HEAD_RADIUS,
                                size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH
                            ),
                            size = Size(
                                HEAD_RADIUS * 2,
                                HEAD_RADIUS * 2
                            ),
                            style = Stroke(STROKE_WIDTH)
                        )
                    }

                    drawLine(
                        bodyColor,
                        start = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                            size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2
                        ),
                        end = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                            (size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH * animatableBody.value)
                        ),
                        STROKE_WIDTH,
                    )

                    if (animatableBody.value == 0f && lives <= 3) {
                        drawLine(
                            bodyColor,
                            start = Offset(
                                size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                                size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2
                            ),
                            end = Offset(
                                size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                                (size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH)
                            ),
                            STROKE_WIDTH,
                        )
                    }

                    drawLine(
                        bodyColor,
                        start = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                            size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD
                        ),
                        end = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH - ARM_LENGTH * animatableLeftArm.value,
                            size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD + ARM_ANGLE * animatableLeftArm.value
                        ),
                        STROKE_WIDTH
                    )
                    if (animatableLeftArm.value == 0f && lives <= 2) {

                        drawLine(
                            bodyColor,
                            start = Offset(
                                size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                                size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD
                            ),
                            end = Offset(
                                size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH - ARM_LENGTH,
                                size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD + ARM_ANGLE
                            ),
                            STROKE_WIDTH
                        )
                    }
                    drawLine(
                        bodyColor,
                        start = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                            size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD
                        ),
                        end = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH + ARM_LENGTH * animatableRightArm.value,
                            size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD + ARM_ANGLE * animatableRightArm.value
                        ),
                        STROKE_WIDTH
                    )

                    if (animatableRightArm.value == 0f && lives <= 1) {
                        drawLine(
                            bodyColor,
                            start = Offset(
                                size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                                size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD
                            ),
                            end = Offset(
                                size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH + ARM_LENGTH,
                                size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD + ARM_ANGLE
                            ),
                            STROKE_WIDTH
                        )
                    }

                    drawLine(
                        bodyColor,
                        start = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                            size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH
                        ),
                        end = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH - HIP_WIDTH * animatableLeftLeg.value,
                            size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_ANGLE * animatableLeftLeg.value
                        ),
                        STROKE_WIDTH
                    )

                    if (animatableLeftLeg.value == 0f && lives == 0) {
                        drawLine(
                            bodyColor,
                            start = Offset(
                                size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                                size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH
                            ),
                            end = Offset(
                                size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH - HIP_WIDTH,
                                size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_ANGLE
                            ),
                            STROKE_WIDTH
                        )
                    }

                    drawLine(
                        bodyColor,
                        start = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH,
                            size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH
                        ),
                        end = Offset(
                            size.width / HANGMAN_WIDTH_OFFSET + BEAM_LENGTH + HIP_WIDTH * animatableRightLeg.value,
                            size.height / HANGMAN_HEIGHT_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_ANGLE * animatableRightLeg.value
                        ),
                        STROKE_WIDTH
                    )
                }
            }
        )
    }
}

/*
 * Copyright 2023 Varsha Kulkarni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *//*

package dev.varshakulkarni.moviehangman.components

import android.annotation.SuppressLint
import android.view.FrameMetrics.ANIMATION_DURATION
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel

data class HangmanDimensions(
    val SCAFFOLD_HEIGHT: Float = 533f,
    val BEAM_LENGTH: Float = 144f,
    val ROPE_LENGTH: Float = 45f,
    val HEAD_RADIUS: Float = 76f,
    val BODY_LENGTH: Float = 144f,
    val ARM_OFFSET_FROM_HEAD: Float = 28f,
    val ARM_LENGTH: Float = 81f,
    val HIP_WIDTH: Float = 36f,
    val ARM_ANGLE: Float = 56f,
    val LEG_ANGLE: Float = 28f,
    val HANGMAN_WIDTH_OFFSET: Float = 2.5f,
    val HANGMAN_HEIGHT_OFFSET: Int = 3,
    val ANIMATION_DURATION: Int = 500,
    val STROKE_WIDTH: Float = 12f
)

sealed class BodyPart {
    object Head : BodyPart()
    object Body : BodyPart()
    object LeftArm : BodyPart()
    object RightArm : BodyPart()
    object LeftLeg : BodyPart()
    object RightLeg : BodyPart()
}

class HangmanViewModel : ViewModel() {
    val animatableHead = Animatable(0f)
    val animatableBody = Animatable(0f)
    val animatableLeftArm = Animatable(0f)
    val animatableRightArm = Animatable(0f)
    val animatableLeftLeg = Animatable(0f)
    val animatableRightLeg = Animatable(0f)
}

@Composable
fun HangmanBody(lives: Int, bodyColor: Color = MaterialTheme.colors.onBackground) {
    val hangmanViewModel = remember { HangmanViewModel() }
    val dimensions = remember { HangmanDimensions() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .semantics { contentDescription = "HangmanBody" },
            onDraw = {
                drawScaffold(size, bodyColor, dimensions)

                if (lives < 6) {
                    drawHead(size, bodyColor, dimensions, hangmanViewModel.animatableHead)
                }

                if (lives < 5) {
                    drawBody(size, bodyColor, dimensions, hangmanViewModel.animatableBody, lives)
                }

                if (lives < 4) {
                    drawLeftArm(size, bodyColor, dimensions, hangmanViewModel.animatableLeftArm, lives)
                }

                if (lives < 3) {
                    drawRightArm(size, bodyColor, dimensions, hangmanViewModel.animatableRightArm, lives)
                }

                if (lives < 2) {
                    drawLeftLeg(size, bodyColor, dimensions, hangmanViewModel.animatableLeftLeg, lives)
                }

                if (lives < 1) {
                    drawRightLeg(size, bodyColor, dimensions, hangmanViewModel.animatableRightLeg, lives)
                }
            }
        )
    }
}

@Composable
fun AnimateBodyPart(lives: Int, animatable: Animatable<Float, AnimationVector1D>) {
    if (lives == 5 || lives == 4 || lives == 3 || lives == 2 || lives == 1 || lives == 0) {
        LaunchedEffect(animatable) {
            animatable.animateTo(
                1f,
                animationSpec = tween(durationMillis = HangmanDimensions().ANIMATION_DURATION, easing = LinearEasing)
            )
        }
    }
}


private fun DrawScope.drawScaffold(size: Size, bodyColor: Color, dimensions: HangmanDimensions) {
    drawRoundRect(
        bodyColor,
        topLeft = Offset(0f, dimensions.HEAD_RADIUS),
        size = Size(size.width, size.height - dimensions.HEAD_RADIUS),
        cornerRadius = CornerRadius(2f, 2f),
        style = Stroke(2f)
    )
    drawLine(
        bodyColor,
        start = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET,
            (size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.SCAFFOLD_HEIGHT)
        ),
        end = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET
        ),
        dimensions.STROKE_WIDTH
    )
    drawLine(
        bodyColor,
        start = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET
        ),
        end = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET
        ),
        dimensions.STROKE_WIDTH
    )
    drawLine(
        bodyColor,
        start = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET
        ),
        end = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH
        ),
        dimensions.STROKE_WIDTH
    )
}

private fun DrawScope.drawHangmanBodyParts(
    size: Size,
    lives: Int,
    bodyColor: Color,
    dimensions: HangmanDimensions,
    hangmanViewModel: HangmanViewModel
) {
    if (lives != 6) {
        drawHead(size, bodyColor, dimensions, hangmanViewModel.animatableHead)
        drawBody(size, bodyColor, dimensions, hangmanViewModel.animatableBody, lives)
        drawLeftArm(size, bodyColor, dimensions, hangmanViewModel.animatableLeftArm, lives)
        drawRightArm(size, bodyColor, dimensions, hangmanViewModel.animatableRightArm, lives)
        drawLeftLeg(size, bodyColor, dimensions, hangmanViewModel.animatableLeftLeg, lives)
        drawRightLeg(size, bodyColor, dimensions, hangmanViewModel.animatableRightLeg, lives)
    }
}


private fun DrawScope.drawHead(
    size: Size,
    bodyColor: Color,
    dimensions: HangmanDimensions,
    animatableHead: Animatable<Float, AnimationVector1D>
) {
    drawArc(
        bodyColor,
        startAngle = 0f,
        sweepAngle = 360f * animatableHead.value,
        false,
        topLeft = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH - dimensions.HEAD_RADIUS,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH
        ),
        size = Size(dimensions.HEAD_RADIUS * 2, dimensions.HEAD_RADIUS * 2),
        style = Stroke(dimensions.STROKE_WIDTH)
    )

    if (animatableHead.value == 0f) {
        drawArc(
            bodyColor,
            startAngle = 0f,
            sweepAngle = 360f,
            false,
            topLeft = Offset(
                size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH - dimensions.HEAD_RADIUS,
                size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH
            ),
            size = Size(dimensions.HEAD_RADIUS * 2, dimensions.HEAD_RADIUS * 2),
            style = Stroke(dimensions.STROKE_WIDTH)
        )
    }

    drawLine(
        bodyColor,
        start = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2
        ),
        end = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
            (size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.BODY_LENGTH * animatableHead.value)
        ),
        dimensions.STROKE_WIDTH,
    )
}

private fun DrawScope.drawBody(
    size: Size,
    bodyColor: Color,
    dimensions: HangmanDimensions,
    animatableBody: Animatable<Float, AnimationVector1D>,
    lives: Int
) {
    if (animatableBody.value == 0f && lives <= 4) {
        drawLine(
            bodyColor,
            start = Offset(
                size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
                size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2
            ),
            end = Offset(
                size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
                (size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.BODY_LENGTH)
            ),
            dimensions.STROKE_WIDTH,
        )
    }

    drawLine(
        bodyColor,
        start = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.ARM_OFFSET_FROM_HEAD
        ),
        end = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH - dimensions.ARM_LENGTH * animatableBody.value,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.ARM_OFFSET_FROM_HEAD + dimensions.ARM_ANGLE * animatableBody.value
        ),
        dimensions.STROKE_WIDTH
    )
}

private fun DrawScope.drawLeftArm(
    size: Size,
    bodyColor: Color,
    dimensions: HangmanDimensions,
    animatableLeftArm: Animatable<Float, AnimationVector1D>,
    lives: Int
) {
    if (animatableLeftArm.value == 0f && lives <= 2) {
        drawLine(
            bodyColor,
            start = Offset(
                size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
                size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.ARM_OFFSET_FROM_HEAD
            ),
            end = Offset(
                size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH - dimensions.ARM_LENGTH,
                size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.ARM_OFFSET_FROM_HEAD + dimensions.ARM_ANGLE
            ),
            dimensions.STROKE_WIDTH
        )
    }

    drawLine(
        bodyColor,
        start = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.ARM_OFFSET_FROM_HEAD
        ),
        end = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH + dimensions.ARM_LENGTH * animatableLeftArm.value,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.ARM_OFFSET_FROM_HEAD + dimensions.ARM_ANGLE * animatableLeftArm.value
        ),
        dimensions.STROKE_WIDTH
    )
}

private fun DrawScope.drawRightArm(
    size: Size,
    bodyColor: Color,
    dimensions: HangmanDimensions,
    animatableRightArm: Animatable<Float, AnimationVector1D>,
    lives: Int
) {
    if (animatableRightArm.value == 0f && lives <= 1) {
        drawLine(
            bodyColor,
            start = Offset(
                size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
                size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.ARM_OFFSET_FROM_HEAD
            ),
            end = Offset(
                size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH + dimensions.ARM_LENGTH,
                size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.ARM_OFFSET_FROM_HEAD + dimensions.ARM_ANGLE
            ),
            dimensions.STROKE_WIDTH
        )
    }

    drawLine(
        bodyColor,
        start = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.ARM_OFFSET_FROM_HEAD
        ),
        end = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH + dimensions.ARM_LENGTH * animatableRightArm.value,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.ARM_OFFSET_FROM_HEAD + dimensions.ARM_ANGLE * animatableRightArm.value
        ),
        dimensions.STROKE_WIDTH
    )
}

private fun DrawScope.drawLeftLeg(
    size: Size,
    bodyColor: Color,
    dimensions: HangmanDimensions,
    animatableLeftLeg: Animatable<Float, AnimationVector1D>,
    lives: Int
) {
    if (animatableLeftLeg.value == 0f && lives == 0) {
        drawLine(
            bodyColor,
            start = Offset(
                size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
                size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.BODY_LENGTH
            ),
            end = Offset(
                size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH - dimensions.HIP_WIDTH,
                size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.BODY_LENGTH + dimensions.LEG_ANGLE
            ),
            dimensions.STROKE_WIDTH
        )
    }

    drawLine(
        bodyColor,
        start = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.BODY_LENGTH
        ),
        end = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH - dimensions.HIP_WIDTH * animatableLeftLeg.value,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.BODY_LENGTH + dimensions.LEG_ANGLE * animatableLeftLeg.value
        ),
        dimensions.STROKE_WIDTH
    )
}

private fun DrawScope.drawRightLeg(
    size: Size,
    bodyColor: Color,
    dimensions: HangmanDimensions,
    animatableRightLeg: Animatable<Float, AnimationVector1D>,
    lives: Int
) {
    drawLine(
        bodyColor,
        start = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.BODY_LENGTH
        ),
        end = Offset(
            size.width / dimensions.HANGMAN_WIDTH_OFFSET + dimensions.BEAM_LENGTH + dimensions.HIP_WIDTH * animatableRightLeg.value,
            size.height / dimensions.HANGMAN_HEIGHT_OFFSET + dimensions.ROPE_LENGTH + dimensions.HEAD_RADIUS * 2 + dimensions.BODY_LENGTH + dimensions.LEG_ANGLE * animatableRightLeg.value
        ),
        dimensions.STROKE_WIDTH
    )
}


@Preview
@Composable
fun HangmanBodyPreview() {
    HangmanBody(lives = 0)
}*/
