import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Student } from '../../models/Student';

@Component({
  selector: 'app-studentsample',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './studentsample.component.html',
  styleUrls: ['./studentsample.component.scss']
})
export class StudentSampleComponent {
  student: Student;
  constructor() {
    this.student = new Student(101, 'Alice Johnson', new Date(2002, 4, 12), '9876543210', 'alice@example.com', '221B Baker Street');
  }
  logStudentAttributes(): void {
    console.log('studentId:', this.student.studentId);
    console.log('fullName:', this.student.fullName);
    console.log('dateOfBirth:', this.student.dateOfBirth);
    console.log('contactNumber:', this.student.contactNumber);
    console.log('email:', this.student.email);
    console.log('address:', this.student.address);
  }
}