using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace NGPS.Models
{
    public class Course
    {
        public Course()
        {
            Routine = new HashSet<Routine>();
        }

        public int id { get; set; }
        public string course_code { get; set; }
        public string course_name { get; set; }
        public int class_id { get; set; }

        public Class Class { get; set; }

        public ICollection<Routine> Routine { get; set; }
    }
}
