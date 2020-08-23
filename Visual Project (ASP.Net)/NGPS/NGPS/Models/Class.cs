using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace NGPS.Models
{
    public class Class
    {
        public Class()
        {
            Course = new HashSet<Course>();
            Routine = new HashSet<Routine>();
            Student = new HashSet<Student>();
        }

        public int id { get; set; }
        public string class_name { get; set; }

        public ICollection<Course> Course { get; set; }
        public ICollection<Routine> Routine { get; set; }
        public ICollection<Student> Student { get; set; }
    }
}
