using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace NGPS.Models
{
    public class Routine
    {
        public int id { get; set; }
        public int class_id { get; set; }
        public int course_id { get; set; }
        public int teacher_id { get; set; }
        public int year_id { get; set; }
        public string class_time { get; set; }



        public Class Class { get; set; }
        public Course Course { get; set; }
        public Teacher Teacher { get; set; }
        public Year Year { get; set; }
      
    }
}
