using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace NGPS.Models
{
    public class Student
    {
        public int id { get; set; }
        public string name { get; set; }
        public string phone { get; set; }
        public string address { get; set; }
        [Required]
        public string user_name { get; set; }
        [Required]
        [DataType(DataType.Password)]
        public string password { get; set; }
        public int class_id { get; set; }
        public int year_id { get; set; }

        public Class Class { get; set; }
        public Year Year { get; set; }

    }
}
