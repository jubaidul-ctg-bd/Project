using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace NGPS.Models
{
    public class Teacher
    {
        public Teacher()
        {
            Routine = new HashSet<Routine>();
        }

        public int id { get; set; }
        public string name { get; set; }
        public string phone { get; set; }
        public string address { get; set; }
        [Required]
        public string user_name { get; set; }
        [Required]
        [DataType(DataType.Password)]
        public string password { get; set; }


        public ICollection<Routine> Routine { get; set; }
    }
}
