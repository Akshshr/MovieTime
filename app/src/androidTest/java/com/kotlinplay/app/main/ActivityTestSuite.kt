package com.kotlinplay.app.main

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    ShowDetailsActivityTest::class
)
class ActivityTestSuite