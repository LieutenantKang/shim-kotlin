package co.shimm.app.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Shim (@PrimaryKey var id: Int?, var title: String?, var category: Int?, var description : String?,
            var src : String?, var thumbnail : String?, var duration: Int?)