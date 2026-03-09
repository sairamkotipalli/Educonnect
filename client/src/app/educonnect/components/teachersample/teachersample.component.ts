import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Teacher } from '../../models/Teacher';

@Component({
  selector: 'app-teachersample',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './teachersample.component.html',
  styleUrls: ['./teachersample.component.scss']
})
export class TeacherSampleComponent {
  teacher: Teacher;
  constructor() {
    this.teacher = new Teacher(501, 'Dr. Robert Smith', '9123456789', 'robert.smith@example.com', 'Physics', 12);
  }
  logTeacherAttributes(): void {
    console.log('teacherId:', this.teacher.teacherId);
    console.log('fullName:', this.teacher.fullName);
    console.log('subject:', this.teacher.subject);
    console.log('contactNumber:', this.teacher.contactNumber);
    console.log('email:', this.teacher.email);
    console.log('yearsOfExperience:', this.teacher.yearsOfExperience);
  }
}