package com.jdiassdev.wheredidmymoneygo.Utils.date;

import java.time.LocalDate;

public class DateRange {
        private LocalDate start;
        private LocalDate end;

        public DateRange(LocalDate start, LocalDate end) {
                this.start = start;
                this.end = end;
        }

        public LocalDate start() {
                return start;
        }

        public LocalDate end() {
                return end;
        }
}
