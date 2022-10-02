package com.halfapum.workmanagerfactory

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleDependency @Inject constructor() {

    var sampleField = "sample"
}