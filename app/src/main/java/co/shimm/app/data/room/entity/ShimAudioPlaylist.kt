package co.shimm.app.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ShimAudioPlaylist (@PrimaryKey var id: Int?, var counselorId: Int?, var title: String?, var category: Int?,
                         var description: String?, var thumbnail: String?, var status: Int,
                         var createdAt: String?, var updatedAt: String?, var deletedAt: String?)