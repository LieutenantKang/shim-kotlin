package co.shimm.app.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ShimCounselor(@PrimaryKey var id: Int?, var name: String?, var picture: String?, var about: String?)