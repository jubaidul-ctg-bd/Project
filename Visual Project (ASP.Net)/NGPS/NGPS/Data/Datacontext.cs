using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using NGPS.Models;

namespace NGPS.Data
{
    public class dataContext : DbContext
    {
        public virtual DbSet<NGPS.Models.Class> Class { get; set; }
        public virtual DbSet<NGPS.Models.Routine> Routine { get; set; }
        public virtual DbSet<NGPS.Models.Teacher> Teacher { get; set; }
        public virtual DbSet<NGPS.Models.Year> Year { get; set; }
        public virtual DbSet<NGPS.Models.Course> Course { get; set; }
        public virtual DbSet<NGPS.Models.User> User { get; set; }
        public virtual DbSet<NGPS.Models.Student> Student { get; set; }

        public dataContext(DbContextOptions<dataContext> options)
            : base(options)
        {

        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Class>(entity =>
            {
                entity.HasKey(e => e.id);
                entity.ToTable("Class");

                entity.Property(e => e.id).IsRequired();

                entity.Property(e => e.class_name).IsRequired();
            });


            modelBuilder.Entity<Course>(entity =>
            {
                entity.HasKey(e => e.id);
                entity.ToTable("Course");

                entity.Property(e => e.course_code).HasColumnName("course_code").IsRequired();

                entity.Property(e => e.course_name).HasColumnName("course_name").IsRequired();

                entity.Property(e => e.class_id).HasColumnName("class_id");

                entity.HasOne(d => d.Class).WithMany(p => p.Course)
                .HasForeignKey(d => d.class_id).OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Course_Class");
            });

            modelBuilder.Entity<Teacher>(entity =>
            {
                entity.HasKey(e => e.id);
                entity.ToTable("Teacher");

                entity.Property(e => e.id).IsRequired();

                entity.Property(e => e.name).IsRequired();

                entity.Property(e => e.phone).IsRequired();

                entity.Property(e => e.address).IsRequired();

                entity.Property(e => e.user_name).IsRequired();

                entity.Property(e => e.password).IsRequired();
            });


            modelBuilder.Entity<Student>(entity =>
            {
                entity.HasKey(e => e.id);
                entity.ToTable("Student");

                entity.Property(e => e.id).IsRequired();

                entity.Property(e => e.name).IsRequired();

                entity.Property(e => e.phone).IsRequired();

                entity.Property(e => e.address).IsRequired();

                entity.Property(e => e.user_name).IsRequired();

                entity.Property(e => e.password).IsRequired();

                entity.Property(e => e.class_id).HasColumnName("class_id");

                entity.Property(e => e.year_id).HasColumnName("year_id");

                entity.HasOne(d => d.Class).WithMany(p => p.Student)
                .HasForeignKey(d => d.class_id).OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Student_Class");

                entity.HasOne(d => d.Year).WithMany(p => p.Student)
                .HasForeignKey(d => d.year_id).OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Student_Year");
            });

            modelBuilder.Entity<Year>(entity =>
            {
                entity.HasKey(e => e.id);
                entity.ToTable("Year");

                entity.Property(e => e.id).IsRequired();

                entity.Property(e => e.year_title).IsRequired();

                entity.Property(e => e.status).IsRequired();

            });

            modelBuilder.Entity<Routine>(entity =>
            {
                entity.HasKey(e => e.id);
                entity.ToTable("Routine");

                entity.Property(e => e.class_id).HasColumnName("class_id");

                entity.Property(e => e.course_id).HasColumnName("course_id");

                entity.Property(e => e.teacher_id).HasColumnName("teacher_id");

                entity.Property(e => e.year_id).HasColumnName("year_id");

                entity.Property(e => e.class_time).HasColumnName("class_time").IsRequired();

                

                entity.HasOne(d => d.Class).WithMany(p => p.Routine)
                .HasForeignKey(d => d.class_id).OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Routine_Class");

                entity.HasOne(d => d.Course).WithMany(p => p.Routine)
                .HasForeignKey(d => d.course_id).OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Routine_Course");

                entity.HasOne(d => d.Teacher).WithMany(p => p.Routine)
                .HasForeignKey(d => d.teacher_id).OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Routine_Teacher");

                entity.HasOne(d => d.Year).WithMany(p => p.Routine)
                .HasForeignKey(d => d.year_id).OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Routine_Year");
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.HasKey(e => e.id);
                entity.ToTable("user");

                entity.Property(e => e.user_name).IsRequired();

                entity.Property(e => e.password).IsRequired();

                entity.Property(e => e.type).IsRequired();
            });


        }
    }

}
