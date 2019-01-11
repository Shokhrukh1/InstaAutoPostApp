package tk.instaautopostapp.ui.taskList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tk.instaautopostapp.R
import tk.instaautopostapp.data.database.repository.content.Content
import tk.instaautopostapp.util.AppConstants
import kotlinx.android.synthetic.main.item_content.view.*
import tk.instaautopostapp.util.GlideApp
import java.text.SimpleDateFormat
import java.util.*

class ContentAdapter(private val contents: List<Content>) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {
    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false))
    }

    override fun getItemCount() = contents.size

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(contents[position])
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(content: Content) {
            calendar.timeInMillis = content.date

            when (content.type) {
                AppConstants.ContentType.IMAGE.type -> {
                    GlideApp.with(itemView)
                            .load(content.filePath)
                            .placeholder(R.drawable.ic_picture)
                            .into(itemView.ivContent)
                }
                AppConstants.ContentType.VIDEO.type -> {
                    GlideApp.with(itemView)
                            .load(R.drawable.ic_film)
                            .into(itemView.ivContent)
                }
            }

            when (content.status) {
                AppConstants.UploadStatus.NONE.status -> {
                    itemView.ivState.setImageURI(null)
                }
                AppConstants.UploadStatus.SUCCESSED.status -> {
                    itemView.ivState.setImageResource(R.drawable.ic_check_green_24dp)
                }
                AppConstants.UploadStatus.FAILED.status -> {
                    itemView.ivState.setImageResource(R.drawable.ic_clear_red_24dp)
                }
            }

            itemView.tvFileName.text = content.fileName
            itemView.tvDate.text = formatter.format(calendar.time)
        }
    }
}