package com.nativedevps.support.base_class.dialog

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.Nullable
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nativedevps.support.base_class.Inflate

/*
* supportFragmentManager.setFragmentResultListener(
                BiometricFragment.RESULT_FRAGMENT,
                this
            ) { key, bundle ->
                if (key == BiometricFragment.RESULT_FRAGMENT) {
                    bundle.getBoolean(BiometricFragment.KEY_IS_AUTHENTICATED, false).apply {
                        setBiometric(this)
                    }
                }
            }
* */
abstract class AbstractBottomSheetDialogFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))*/
        preInit()
    }

    open fun preInit() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)

        onInit()
        return binding.root
    }

    abstract fun onInit()

    /*override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }*/

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                } else {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
                val dialog = dialog as BottomSheetDialog?
                val bottomSheet: FrameLayout =
                    dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<View?>(bottomSheet)
                behavior.state = initialExpandState()
                behavior.isDraggable = setDraggable()
                setupBehavior(behavior)
                setupWindow(bottomSheet)
                /*behavior.peekHeight = 0 // Remove this line to hide a dark background if you manually hide the dialog.*/
            }
        })
    }

    open fun setupWindow(bottomSheet: FrameLayout) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    /*
    * override for behaviour changes
    * */
    open fun setupBehavior(behavior: BottomSheetBehavior<*>) {
        //noop
    }

    /*override fun show(manager: FragmentManager, tag: String?) {
        if (isAdded || isVisible) {
            return
        }
        super.show(manager, tag)
    }*/

    open fun setDraggable(): Boolean {
        return true
    }

    open fun initialExpandState(): Int {
        return BottomSheetBehavior.STATE_EXPANDED
    }
}