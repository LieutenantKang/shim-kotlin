package co.shimm.app.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ShimCounselor(@PrimaryKey var id: Int?, var email: String?, var name: String?, var phone: String?, var picture: String?,
                    var about: String?, var online30Cost: Int?, var online60Cost: Int?, var offline30Cost: Int?,
                    var offline60Cost: Int?, var centerLocation: String?, var centerInformation: String?)