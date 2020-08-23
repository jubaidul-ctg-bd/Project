using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace NGPS.Models
{
    public class User
    {
        public int id { get; set; }
        [Required]
        public string user_name { get; set; }
        [Required]
        [DataType(DataType.Password)]
        public string password { get; set; }
        public string type { get; set; }
    }
}
