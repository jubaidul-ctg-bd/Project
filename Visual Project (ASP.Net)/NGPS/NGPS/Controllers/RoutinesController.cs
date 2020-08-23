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
    public class RoutinesController : Controller
    {
        private readonly dataContext _context;

        public RoutinesController(dataContext context)
        {
            _context = context;
        }

        // GET: Students/Details/5
        public async Task<IActionResult> Index()
        {
            var type = HttpContext.Session.GetString("type");
            var id = HttpContext.Session.GetInt32("id");
            var class_id = HttpContext.Session.GetInt32("class_id");
            var year_id = HttpContext.Session.GetInt32("year_id");
        
            if (type == "Teacher")
            {
                var dataContext = _context.Routine.Include(r => r.Class).Include(r => r.Course).Include(r => r.Teacher).Include(r => r.Year);
                return View(await dataContext.Where(m => m.teacher_id == id).OrderBy(m => m.class_time).ToListAsync());
            }
            else if (type == "Student")
            {
                var dataContext = _context.Routine.Include(r => r.Class).Include(r => r.Course).Include(r => r.Teacher).Include(r => r.Year);
                return View(await dataContext.Where(m => m.class_id == class_id && m.year_id == year_id).OrderBy(m => m.class_time).ToListAsync());
            }
            else if (type == "Admin")
            {
                var dataContext = _context.Routine.Include(r => r.Class).Include(r => r.Course).Include(r => r.Teacher).Include(r => r.Year);
                return View(await dataContext.OrderBy(m => m.class_id).OrderBy(m => m.class_time).ToListAsync());
            }
            else
            {
                return RedirectToAction("Login", "Users");
            }
        }

        private int ConvertToInt32(string v)
        {
            throw new NotImplementedException();
        }



        // GET: Routines/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var routine = await _context.Routine
                .Include(r => r.Class)
                .Include(r => r.Course)
                .Include(r => r.Teacher)
                .Include(r => r.Year)
                .FirstOrDefaultAsync(m => m.id == id);
            if (routine == null)
            {
                return NotFound();
            }

            return View(routine);
        }

        // GET: Routines/Create
        public IActionResult Create(String msg)
        {
            var type = HttpContext.Session.GetString("type");
            if (type != "Admin")
            {
                return RedirectToAction("Login", "Users");
            }

            ViewData["class_id"] = new SelectList(_context.Class, "id", "class_name");
            ViewData["course_id"] = new SelectList(_context.Course, "id", "course_code");
            ViewData["teacher_id"] = new SelectList(_context.Teacher, "id", "name");
            ViewData["year_id"] = new SelectList(_context.Year, "id", "year_title");

            ViewBag.msg = msg;
            return View();
        }

        // POST: Routines/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("id,class_id,course_id,teacher_id,year_id,class_time")] Routine routine)
        {
            var data = await _context.Routine
                .FirstOrDefaultAsync(m => (m.teacher_id == routine.teacher_id && m.class_time == routine.class_time) || (m.class_id == routine.class_id && m.class_time == routine.class_time));
            if(data != null)
            {
                return RedirectToAction(nameof(Create), new { msg = "Conflict"});
            }
            else { 
                if (ModelState.IsValid)
                {
                    _context.Add(routine);
                    await _context.SaveChangesAsync();
                    return RedirectToAction(nameof(Index));
                }
                ViewData["class_id"] = new SelectList(_context.Class, "id", "class_name", routine.class_id);
                ViewData["course_id"] = new SelectList(_context.Course, "id", "course_code", routine.course_id);
                ViewData["teacher_id"] = new SelectList(_context.Teacher, "id", "name", routine.teacher_id);
                ViewData["year_id"] = new SelectList(_context.Year, "id", "year_title", routine.year_id);
                return View(routine);
            }
        }

        // GET: Routines/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var routine = await _context.Routine.FindAsync(id);
            if (routine == null)
            {
                return NotFound();
            }
            ViewData["class_id"] = new SelectList(_context.Class, "id", "class_name", routine.class_id);
            ViewData["course_id"] = new SelectList(_context.Course, "id", "course_code", routine.course_id);
            ViewData["teacher_id"] = new SelectList(_context.Teacher, "id", "name", routine.teacher_id);
            ViewData["year_id"] = new SelectList(_context.Year, "id", "year_title", routine.year_id);
            return View(routine);
        }

        // POST: Routines/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("id,class_id,course_id,teacher_id,year_id,class_time")] Routine routine)
        {
            if (id != routine.id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(routine);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!RoutineExists(routine.id))
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
            ViewData["class_id"] = new SelectList(_context.Class, "id", "class_name", routine.class_id);
            ViewData["course_id"] = new SelectList(_context.Course, "id", "course_code", routine.course_id);
            ViewData["teacher_id"] = new SelectList(_context.Teacher, "id", "name", routine.teacher_id);
            ViewData["year_id"] = new SelectList(_context.Year, "id", "year_title", routine.year_id);
            return View(routine);
        }

        // GET: Routines/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var routine = await _context.Routine
                .Include(r => r.Class)
                .Include(r => r.Course)
                .Include(r => r.Teacher)
                .Include(r => r.Year)
                .FirstOrDefaultAsync(m => m.id == id);
            if (routine == null)
            {
                return NotFound();
            }

            return View(routine);
        }

        // POST: Routines/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var routine = await _context.Routine.FindAsync(id);
            _context.Routine.Remove(routine);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool RoutineExists(int id)
        {
            return _context.Routine.Any(e => e.id == id);
        }
    }
}
