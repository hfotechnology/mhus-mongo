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
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.mhus.lib.adb.Persistable;
import de.mhus.lib.annotations.adb.DbPersistent;
import de.mhus.lib.annotations.adb.DbPrimaryKey;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.Indexes;
import dev.morphia.annotations.Reference;

@Entity("employees")
@Indexes(@Index(value = "salary", fields = @Field("salary")))
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

    public List<Employee> getDirectReports() {
        return directReports;
    }

    public void setDirectReports(List<Employee> directReports) {
        this.directReports = directReports;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @DbPrimaryKey private UUID id;
    @DbPersistent private String name;
    @Reference private Employee manager;
    @Reference private List<Employee> directReports = new LinkedList<Employee>();
    @DbPersistent private Double salary;
    @DbPersistent private HashMap<String, String> values = new HashMap<>();

    @Override
    public String toString() {
        return id + " " + name;
    }

    public HashMap<String, String> getValues() {
        return values;
    }
}
