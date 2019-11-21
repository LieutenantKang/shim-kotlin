package co.shimm.app.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Music(@PrimaryKey var id: Int?, var title: String?, var category: String?)