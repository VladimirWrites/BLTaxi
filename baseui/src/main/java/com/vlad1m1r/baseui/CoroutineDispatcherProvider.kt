package com.vlad1m1r.baseui

import kotlinx.coroutines.CoroutineDispatcher

class CoroutineDispatcherProvider (val main: CoroutineDispatcher, val io: CoroutineDispatcher)