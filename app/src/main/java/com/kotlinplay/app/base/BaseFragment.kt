package com.kotlinplay.app.base


import androidx.fragment.app.Fragment
import com.kotlinplay.api.API
import com.kotlinplay.app.storage.AppPreferences

/**
 * A simple [Fragment] subclass.
 */
class BaseFragment : Fragment() {

    protected fun getAppPreferences(): AppPreferences? {
        return if (activity != null) (activity as BaseActivity).getAppPreferences() else null
    }

    protected fun getAPI(): API? {
        return if (activity != null) (activity as BaseActivity).getApi() else null
    }

    enum class FragmentEvent {
        ATTACH, DETACH,
        START, STOP
    }



}
