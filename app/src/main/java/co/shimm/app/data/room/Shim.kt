package co.shimm.app.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Shim {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var title: String? = null
    var thumbnailUrl : String? = null
}