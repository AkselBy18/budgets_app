package com.example.budgetapp.adapters
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.R
import com.example.budgetapp.data.database_new.entities.EntityMovement
import java.text.SimpleDateFormat
import java.util.Locale

class MovementAdapter(
    private val movements: List<EntityMovement>,
    private val onEditClick: (EntityMovement) -> Unit,
    private val onDeleteClick: (EntityMovement) -> Unit
): RecyclerView.Adapter<MovementAdapter.MovementViewHolder>() {

    inner class MovementViewHolder(val itemView: View) : RecyclerView.ViewHolder (itemView) {
        val textAmount: TextView = itemView.findViewById(R.id.textAmount)
        val textCategory: TextView = itemView.findViewById(R.id.textCategory)
        val textSign: TextView = itemView.findViewById(R.id.textSign)
        val textDetail: TextView = itemView.findViewById(R.id.textDetail)
        val textDate: TextView = itemView.findViewById(R.id.textDate)
        val btnMenu: ImageButton = itemView.findViewById(R.id.btnMenu)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_movement, parent, false)
        return MovementViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovementViewHolder, position: Int) {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE dd, MMMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(movements[position].date)
        val movement = movements[position]
        val colorText = if (movement.sign == "+") "#0BFF96".toColorInt() else "#FF0B55".toColorInt()
        holder.textAmount.text = "${movement.amount}"
        holder.textSign.text = movement.sign
        holder.textCategory.text = movement.categoryName
        holder.textDetail.text = movement.detail
        holder.textDate.text = outputFormat.format(date!!)
        holder.textSign.setTextColor(colorText)
        holder.textAmount.setTextColor(colorText)

        holder.btnMenu.setOnClickListener {
            val popup = PopupMenu(holder.btnMenu.context, holder.btnMenu)
            popup.inflate(R.menu.item_movement_menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> {
                        onEditClick(movement)
                        true
                    }
                    R.id.action_delete -> {
                        onDeleteClick(movement)
                        true
                    }
                    else -> false
                }
            }
            try {
                popup.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    override fun getItemCount(): Int = movements.size
}