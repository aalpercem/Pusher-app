package com.projecta.pusher.ui.post

import android.widget.ImageView
import com.projecta.pusher.R

interface Change {
    val friend1: ImageView
    val friend2: ImageView
    val friend3: ImageView
    val friend4: ImageView
    val friend5: ImageView
    var selectPer: Int
    fun changeImg()
}
class ChangeOne(
    override val friend1: ImageView,
    override val friend2: ImageView,
    override val friend3: ImageView,
    override val friend4: ImageView,
    override val friend5: ImageView,
    override var selectPer: Int
) : Change {
    override fun changeImg() {
        friend1.setImageResource(R.drawable.deactive)
        friend2.setImageResource(R.drawable.deactive)
        friend3.setImageResource(R.drawable.deactive)
        friend4.setImageResource(R.drawable.deactive)
        friend5.setImageResource(R.drawable.deactive)
        friend1.setImageResource(R.drawable.active)
        selectPer = 1
    }
}

class ChangeTwo(
    override val friend1: ImageView,
    override val friend2: ImageView,
    override val friend3: ImageView,
    override val friend4: ImageView,
    override val friend5: ImageView,
    override var selectPer: Int
) : Change {
    override fun changeImg() {
        friend1.setImageResource(R.drawable.deactive)
        friend2.setImageResource(R.drawable.deactive)
        friend3.setImageResource(R.drawable.deactive)
        friend4.setImageResource(R.drawable.deactive)
        friend5.setImageResource(R.drawable.deactive)
        friend1.setImageResource(R.drawable.active)
        friend2.setImageResource(R.drawable.active)
        selectPer = 2
    }
}

class ChangeThree(
    override val friend1: ImageView,
    override val friend2: ImageView,
    override val friend3: ImageView,
    override val friend4: ImageView,
    override val friend5: ImageView,
    override var selectPer: Int
) : Change {
    override fun changeImg() {
        friend1.setImageResource(R.drawable.deactive)
        friend2.setImageResource(R.drawable.deactive)
        friend3.setImageResource(R.drawable.deactive)
        friend4.setImageResource(R.drawable.deactive)
        friend5.setImageResource(R.drawable.deactive)
        friend1.setImageResource(R.drawable.active)
        friend2.setImageResource(R.drawable.active)
        friend3.setImageResource(R.drawable.active)
        selectPer = 3
    }
}

class ChangeFour(
    override val friend1: ImageView,
    override val friend2: ImageView,
    override val friend3: ImageView,
    override val friend4: ImageView,
    override val friend5: ImageView,
    override var selectPer: Int
) : Change {
    override fun changeImg() {
        friend1.setImageResource(R.drawable.deactive)
        friend2.setImageResource(R.drawable.deactive)
        friend3.setImageResource(R.drawable.deactive)
        friend4.setImageResource(R.drawable.deactive)
        friend5.setImageResource(R.drawable.deactive)
        friend1.setImageResource(R.drawable.active)
        friend2.setImageResource(R.drawable.active)
        friend3.setImageResource(R.drawable.active)
        friend4.setImageResource(R.drawable.active)
        selectPer = 4
    }
}

class ChangeFive(
    override val friend1: ImageView,
    override val friend2: ImageView,
    override val friend3: ImageView,
    override val friend4: ImageView,
    override val friend5: ImageView,
    override var selectPer: Int
) : Change {
    override fun changeImg() {
        friend1.setImageResource(R.drawable.deactive)
        friend2.setImageResource(R.drawable.deactive)
        friend3.setImageResource(R.drawable.deactive)
        friend4.setImageResource(R.drawable.deactive)
        friend5.setImageResource(R.drawable.deactive)
        friend1.setImageResource(R.drawable.active)
        friend2.setImageResource(R.drawable.active)
        friend3.setImageResource(R.drawable.active)
        friend4.setImageResource(R.drawable.active)
        friend4.setImageResource(R.drawable.active)
        selectPer = 5
    }
}
class StrategyPattern(
    private var change: Change
) {
    fun fChange():Int {
        change.changeImg()
        val selectPer = change.selectPer
        return selectPer
    }
}