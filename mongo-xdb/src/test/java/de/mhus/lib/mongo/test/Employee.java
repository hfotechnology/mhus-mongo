/**
 * Copyright 2018 Mike Hummel
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mhus.lib.mongo.test;

import java.util.HashMap;
import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import de.mhus.lib.adb.Persistable;

@Entity("employees")
@Indexes(@Index(fields = @Field("salary")))
public class Employee implements Persistable {

    public Employee() {}

    public Employee(String name, double d) {
        this.setName(name);
        this.setSalary(d);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    //    public List<Employee> getDirectReports() {
    //        return directReports;
    //    }
    //
    //    public void setDirectReports(List<Employee> directReports) {
    //        this.directReports = directReports;
    //    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Id @Property private UUID id;
    @Property private String name;
    @Reference private Employee manager;
    //    @Reference private List<Employee> directReports = new LinkedList<Employee>();
    @Property private Double salary;
    @Property private HashMap<String, String> values = new HashMap<>();

    @Override
    public String toString() {
        return id + " " + name;
    }

    public HashMap<String, String> getValues() {
        return values;
    }
}
