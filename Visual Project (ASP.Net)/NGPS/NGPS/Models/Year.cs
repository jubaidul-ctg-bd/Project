using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace NGPS.Models
{
    public class Year
    {
        public Year()
        {
            Routine = new HashSet<Routine>();
            Student = new HashSet<Student>();
        }

        public int id { get; set; }
        public string year_title { get; set; }
        public bool status { get; set; }


        public ICollection<Routine> Routine { get; set; }
        public ICollection<Student> Student { get; set; }
    }
}
