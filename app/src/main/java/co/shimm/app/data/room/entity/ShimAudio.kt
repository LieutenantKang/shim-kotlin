package co.shimm.app.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class ShimAudio(@PrimaryKey var id: Int?, var playlistId: Int?, var title: String?, var description: String?,
                var thumbnail: String?, var src: String?, var duration: Int?,
                var createdAt: String?, var updatedAt: String?, var deletedAt: String?)