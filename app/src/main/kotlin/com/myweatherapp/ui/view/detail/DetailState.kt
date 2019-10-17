package com.myweatherapp.ui.view.detail

import io.uniflow.core.flow.UIState

data class DetailState(val icon: String,
                       val day: String,
                       val fullText: String,
                       val wind: String,
                       val temperature: String,
                       val humidity: String,
                       val color: Int) : UIState()