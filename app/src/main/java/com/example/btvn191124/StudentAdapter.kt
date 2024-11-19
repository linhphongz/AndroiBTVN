package com.example.btvn191124

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(private val students: MutableList<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
            parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        var studentz: StudentModel = students[position]
        val currentPosition = position

        holder.textStudentName.text = studentz.studentName
        holder.textStudentId.text = studentz.studentId
        holder.imageEdit.setOnClickListener {

            val dialog = AlertDialog.Builder(holder.itemView.context)
            dialog.setTitle("Sửa thông tin sinh viên")
            var dialogview = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_input,null)
            dialog.setView(dialogview)
            val edtName: EditText = dialogview.findViewById(R.id.input_name)
            val edtStudentId: EditText = dialogview.findViewById(R.id.input_mssv)

            edtName.setText(studentz.studentName)
            edtStudentId.setText(studentz.studentId)
            dialog.setPositiveButton("Save"){
                _,_ ->
                studentz.studentName = edtName.text.toString()
                studentz.studentId = edtStudentId.text.toString()
                notifyItemChanged(currentPosition)
            }
            dialog.show()
    }

        holder.imageRemove.setOnClickListener {
            val dialog = AlertDialog.Builder(holder.itemView.context)
            var dialogview = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_remove_student,null)
            dialog.setView(dialogview)
            dialog.setPositiveButton("Dong y"){
                    _,_ ->
                var temp_name = studentz.studentName
                var temp_mssv = studentz.studentId
                var deletedStudent = StudentModel(temp_name,temp_mssv)
                students.removeAt(currentPosition)
                notifyItemRemoved(currentPosition)
                val snackbar = Snackbar.make(holder.itemView, "Sinh viên ${studentz.studentName} đã bị xóa", Snackbar.LENGTH_LONG)
                snackbar.setAction("Undo") {
                    students.add(currentPosition, deletedStudent)
                    notifyItemInserted(currentPosition)

                    Toast.makeText(holder.itemView.context, "Đã hoàn tác xóa sinh viên", Toast.LENGTH_SHORT).show()
                }
                snackbar.show()
            }
            dialog.setNeutralButton("Huy bo"){
                    _,_ ->
            }
            dialog.show()
        }

    }

    fun addStudent(student: StudentModel) {
        students.add(student)
        notifyItemInserted(students.size - 1)
    }
}