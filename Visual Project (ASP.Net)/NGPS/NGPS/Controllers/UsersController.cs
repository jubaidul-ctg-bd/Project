using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using NGPS.Data;
using NGPS.Models;


namespace NGPS.Controllers
{
    public class UsersController : Controller
    {
        private readonly dataContext _context;

        public UsersController(dataContext context)
        {
            _context = context;
        }

        public IActionResult Logout()
        {
            @HttpContext.Session.Clear();
            return RedirectToAction("Login", "Users");
        }

        [HttpGet]
        public IActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Login(User u)
        {
            
            if (u.type == "Admin")
            {
                var user = _context.User.Where(d => d.user_name == u.user_name && d.password == u.password).FirstOrDefault();
                if (user != null)
                {
                    // HttpContext.Session.Set("type", test["type"]);
                    HttpContext.Session.SetString("type", "Admin");
                    HttpContext.Session.SetString("user_name", user.user_name);
                    return RedirectToAction("Index", "Routines");
                }
                else
                {
                    ViewBag.msg = "Username or Password or Type is incorrect";
                }
            }
            else if(u.type == "Teacher")
            {
                var teacher = _context.Teacher.Where(d => d.user_name == u.user_name && d.password == u.password).FirstOrDefault();
                // HttpContext.Session.Set("type", test["type"]);
                if (teacher != null)
                {
                    HttpContext.Session.SetInt32("id", teacher.id);
                    HttpContext.Session.SetString("type", "Teacher");
                    HttpContext.Session.SetString("user_name", teacher.user_name);
                    return RedirectToAction("Index", "Routines");
                }
                else
                {
                    ViewBag.msg = "Username or Password or Type is incorrect";
                }
            } 
            else
            {
                var student = _context.Student.Where(d => d.user_name == u.user_name && d.password == u.password).FirstOrDefault();
                // HttpContext.Session.Set("type", test["type"]);
                if (student != null)
                {
                    HttpContext.Session.SetString("type", "Student");
                    HttpContext.Session.SetString("user_name", student.user_name);
                    HttpContext.Session.SetInt32("class_id", student.class_id);
                    HttpContext.Session.SetInt32("year_id", student.year_id);
                    return RedirectToAction("Index", "Routines");
                }
                else
                {
                    ViewBag.msg = "Username or Password or Type is incorrect";
                }
            }
            
          
            return View();
        }

        private string ConvertToString(int id)
        {
            throw new NotImplementedException();
        }


        // GET: Users
        public async Task<IActionResult> Index()
        {
            return View(await _context.User.ToListAsync());
        }

        // GET: Users/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var user = await _context.User
                .FirstOrDefaultAsync(m => m.user_name == id);
            if (user == null)
            {
                return NotFound();
            }

            return View(user);
        }

        // GET: Users/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Users/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("user_name,password,type")] User user)
        {
            if (ModelState.IsValid)
            {
                _context.Add(user);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(user);
        }

        // GET: Users/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var user = await _context.User.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }
            return View(user);
        }

        // POST: Users/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("user_name,password,type")] User user)
        {
            if (id != user.user_name)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(user);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!UserExists(user.user_name))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(user);
        }

        // GET: Users/Delete/5
        public async Task<IActionResult> Delete(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var user = await _context.User
                .FirstOrDefaultAsync(m => m.user_name == id);
            if (user == null)
            {
                return NotFound();
            }

            return View(user);
        }

        // POST: Users/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            var user = await _context.User.FindAsync(id);
            _context.User.Remove(user);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool UserExists(string id)
        {
            return _context.User.Any(e => e.user_name == id);
        }
    }
}
