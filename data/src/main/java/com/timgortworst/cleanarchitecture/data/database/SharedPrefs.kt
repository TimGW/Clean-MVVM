package com.timgortworst.cleanarchitecture.data.database

class SharedPrefs(
    private val spm: SharedPrefManager
) {

    fun setOnboardingDone(isDone: Boolean) {
        spm.setBoolValue(SHARED_PREF_ONBOARDING, isDone)
    }

    fun isOnboardingDone() = spm.getBoolValue(SHARED_PREF_ONBOARDING)

    companion object {
        const val SHARED_PREF_ONBOARDING = "SHARED_PREF_ONBOARDING"
    }
}
